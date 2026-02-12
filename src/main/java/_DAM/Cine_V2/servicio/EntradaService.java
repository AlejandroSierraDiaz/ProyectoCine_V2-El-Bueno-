package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.entrada.EntradaRequest;
import _DAM.Cine_V2.dto.entrada.EntradaResponse;
import _DAM.Cine_V2.mapper.EntradaMapper;
import _DAM.Cine_V2.modelo.Entrada;
import _DAM.Cine_V2.modelo.EstadoEntrada;
import _DAM.Cine_V2.modelo.Funcion;
import _DAM.Cine_V2.repositorio.EntradaRepository;
import _DAM.Cine_V2.repositorio.FuncionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final FuncionRepository funcionRepository;
    private final EntradaMapper entradaMapper;

    @Transactional(readOnly = true)
    public List<EntradaResponse> findAll() {
        return entradaRepository.findAll().stream()
                .map(entradaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EntradaResponse findById(Long id) {
        return entradaRepository.findById(id)
                .map(entradaMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));
    }

    @Transactional
    public EntradaResponse create(EntradaRequest entradaRequest) {
        // Validation: Seat availability
        if (isSeatOccupied(entradaRequest.funcionId(), entradaRequest.fila(), entradaRequest.asiento())) {
            throw new RuntimeException(
                    "El asiento " + entradaRequest.fila() + "-" + entradaRequest.asiento() + " ya está ocupado.");
        }

        Entrada entrada = entradaMapper.toEntity(entradaRequest);

        if (entradaRequest.funcionId() != null) {
            Funcion funcion = funcionRepository.findById(entradaRequest.funcionId())
                    .orElseThrow(
                            () -> new RuntimeException("Funcion no encontrada con ID: " + entradaRequest.funcionId()));
            entrada.setFuncion(funcion);
        }

        // Note: We do not set Venta here as EntradaRequest does not include ventaId.
        // Entradas are typically created via VentaService.

        if (entrada.getEstado() == null) {
            entrada.setEstado(EstadoEntrada.VENDIDA);
        }

        Entrada saved = entradaRepository.save(entrada);
        return entradaMapper.toResponse(saved);
    }

    @Transactional
    public EntradaResponse update(Long id, EntradaRequest entradaRequest) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));

        // If seat changing, check availability?
        // For simplicity, we assume generic update logic.
        // Ideally should check if fila/asiento changed and if new one is occupied.
        if (entradaRequest.fila() != entrada.getFila() || entradaRequest.asiento() != entrada.getAsiento()) {
            if (isSeatOccupied(entradaRequest.funcionId(), entradaRequest.fila(), entradaRequest.asiento())) {
                throw new RuntimeException("El nuevo asiento ya está ocupado.");
            }
        }

        entradaMapper.updateEntityFromRequest(entradaRequest, entrada);

        if (entradaRequest.funcionId() != null) {
            Funcion funcion = funcionRepository.findById(entradaRequest.funcionId())
                    .orElseThrow(
                            () -> new RuntimeException("Funcion no encontrada con ID: " + entradaRequest.funcionId()));
            entrada.setFuncion(funcion);
        }

        Entrada saved = entradaRepository.save(entrada);
        return entradaMapper.toResponse(saved);
    }

    public boolean isSeatOccupied(Long funcionId, int fila, int asiento) {
        List<Entrada> entradas = entradaRepository.findByFuncionId(funcionId);
        return entradas.stream().anyMatch(
                e -> e.getFila() == fila && e.getAsiento() == asiento && e.getEstado() != EstadoEntrada.CANCELADA);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!entradaRepository.existsById(id)) {
            throw new RuntimeException("Entrada no encontrada con ID: " + id);
        }
        entradaRepository.deleteById(id);
    }
}
