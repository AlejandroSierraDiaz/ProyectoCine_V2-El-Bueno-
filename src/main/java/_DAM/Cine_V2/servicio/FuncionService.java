package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.funcion.FuncionRequest;
import _DAM.Cine_V2.dto.funcion.FuncionResponse;
import _DAM.Cine_V2.mapper.FuncionMapper;
import _DAM.Cine_V2.modelo.Funcion;
import _DAM.Cine_V2.modelo.Pelicula;
import _DAM.Cine_V2.modelo.Sala;
import _DAM.Cine_V2.repositorio.FuncionRepository;
import _DAM.Cine_V2.repositorio.PeliculaRepository;
import _DAM.Cine_V2.repositorio.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionService {

    private final FuncionRepository funcionRepository;
    private final PeliculaRepository peliculaRepository;
    private final SalaRepository salaRepository;
    private final FuncionMapper funcionMapper;

    @Transactional(readOnly = true)
    public List<FuncionResponse> findAll() {
        return funcionRepository.findAll().stream()
                .map(funcionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FuncionResponse findById(Long id) {
        return funcionRepository.findById(id)
                .map(funcionMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Funcion no encontrada con ID: " + id));
    }

    @Transactional
    public FuncionResponse create(FuncionRequest funcionRequest) {
        Funcion funcion = funcionMapper.toEntity(funcionRequest);
        assignDependencies(funcion, funcionRequest);
        Funcion saved = funcionRepository.save(funcion);
        return funcionMapper.toResponse(saved);
    }

    @Transactional
    public FuncionResponse update(Long id, FuncionRequest funcionRequest) {
        Funcion funcion = funcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcion no encontrada con ID: " + id));

        funcionMapper.updateEntityFromRequest(funcionRequest, funcion);
        assignDependencies(funcion, funcionRequest);

        Funcion saved = funcionRepository.save(funcion);
        return funcionMapper.toResponse(saved);
    }

    private void assignDependencies(Funcion funcion, FuncionRequest request) {
        if (request.peliculaId() != null) {
            Pelicula pelicula = peliculaRepository.findById(request.peliculaId())
                    .orElseThrow(() -> new RuntimeException("Pelicula no encontrada con ID: " + request.peliculaId()));
            funcion.setPelicula(pelicula);
        }

        if (request.salaId() != null) {
            Sala sala = salaRepository.findById(request.salaId())
                    .orElseThrow(() -> new RuntimeException("Sala no encontrada con ID: " + request.salaId()));
            funcion.setSala(sala);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!funcionRepository.existsById(id)) {
            throw new RuntimeException("Funcion no encontrada con ID: " + id);
        }
        funcionRepository.deleteById(id);
    }
}
