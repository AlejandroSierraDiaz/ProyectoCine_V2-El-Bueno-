package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.entrada.EntradaRequest;
import _DAM.Cine_V2.dto.venta.VentaRequest;
import _DAM.Cine_V2.dto.venta.VentaResponse;
import _DAM.Cine_V2.mapper.EntradaMapper;
import _DAM.Cine_V2.mapper.VentaMapper;
import _DAM.Cine_V2.modelo.*;
import _DAM.Cine_V2.repositorio.EntradaRepository;
import _DAM.Cine_V2.repositorio.FuncionRepository;
import _DAM.Cine_V2.repositorio.UsuarioRepository;
import _DAM.Cine_V2.repositorio.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FuncionRepository funcionRepository;
    private final EntradaRepository entradaRepository;
    private final VentaMapper ventaMapper;
    private final EntradaMapper entradaMapper;

    public List<VentaResponse> findAll() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public VentaResponse findById(Long id) {
        return ventaRepository.findById(id)
                .map(ventaMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    @Transactional
    public VentaResponse create(VentaRequest ventaRequest) {
        Venta venta = ventaMapper.toEntity(ventaRequest);

        // Set Defaults
        venta.setFecha(LocalDateTime.now());
        if (venta.getEstado() == null) {
            venta.setEstado("COMPLETADA"); // Default status
        }

        if (ventaRequest.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(ventaRequest.usuarioId())
                    .orElseThrow(
                            () -> new RuntimeException("Usuario no encontrado con ID: " + ventaRequest.usuarioId()));
            venta.setUsuario(usuario);
        }

        processEntradas(venta, ventaRequest.entradas());

        Venta saved = ventaRepository.save(venta);
        return ventaMapper.toResponse(saved);
    }

    @Transactional
    public VentaResponse update(Long id, VentaRequest ventaRequest) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        ventaMapper.updateEntityFromRequest(ventaRequest, venta);

        if (ventaRequest.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(ventaRequest.usuarioId())
                    .orElseThrow(
                            () -> new RuntimeException("Usuario no encontrado con ID: " + ventaRequest.usuarioId()));
            venta.setUsuario(usuario);
        }

        // If entradas are provided in update, we replace them (due to
        // orphanRemoval=true in Entity)
        if (ventaRequest.entradas() != null) {
            venta.getEntradas().clear();
            processEntradas(venta, ventaRequest.entradas());
        }

        Venta saved = ventaRepository.save(venta);
        return ventaMapper.toResponse(saved);
    }

    private void processEntradas(Venta venta, Set<EntradaRequest> entradasRequests) {
        if (entradasRequests != null && !entradasRequests.isEmpty()) {
            double importeCalculated = 0.0;

            for (EntradaRequest eReq : entradasRequests) {
                if (eReq.funcionId() == null)
                    throw new RuntimeException("Entrada sin funcion ID");

                Funcion funcion = funcionRepository.findById(eReq.funcionId())
                        .orElseThrow(() -> new RuntimeException("Funcion no encontrada " + eReq.funcionId()));

                // Check availability
                boolean occupied = entradaRepository.findByFuncionId(funcion.getId()).stream()
                        .anyMatch(e -> e.getFila() == eReq.fila() && e.getAsiento() == eReq.asiento()
                                && e.getEstado() != EstadoEntrada.CANCELADA);

                if (occupied) {
                    throw new RuntimeException("Asiento ocupado: " + eReq.fila() + "-" + eReq.asiento());
                }

                Entrada entrada = entradaMapper.toEntity(eReq);
                entrada.setFuncion(funcion);
                entrada.setVenta(venta);

                if (entrada.getEstado() == null) {
                    entrada.setEstado(EstadoEntrada.VENDIDA);
                }

                venta.getEntradas().add(entrada);
                importeCalculated += funcion.getPrecio();
            }
            venta.setImporteTotal(importeCalculated);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con ID: " + id);
        }
        ventaRepository.deleteById(id);
    }
}
