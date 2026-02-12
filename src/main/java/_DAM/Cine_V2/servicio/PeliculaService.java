package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.pelicula.PeliculaRequest;
import _DAM.Cine_V2.dto.pelicula.PeliculaResponse;
import _DAM.Cine_V2.mapper.PeliculaMapper;
import _DAM.Cine_V2.modelo.Actor;
import _DAM.Cine_V2.modelo.Director;
import _DAM.Cine_V2.modelo.Pelicula;
import _DAM.Cine_V2.repositorio.ActorRepository;
import _DAM.Cine_V2.repositorio.DirectorRepository;
import _DAM.Cine_V2.repositorio.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final PeliculaMapper peliculaMapper;

    @Transactional(readOnly = true)
    public List<PeliculaResponse> findAll() {
        return peliculaRepository.findAll().stream()
                .map(peliculaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PeliculaResponse findById(Long id) {
        return peliculaRepository.findById(id)
                .map(peliculaMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Pelicula no encontrada con ID: " + id));
    }

    @Transactional
    public PeliculaResponse create(PeliculaRequest peliculaRequest) {
        Pelicula pelicula = peliculaMapper.toEntity(peliculaRequest);
        assignDependencies(pelicula, peliculaRequest);
        Pelicula saved = peliculaRepository.save(pelicula);
        return peliculaMapper.toResponse(saved);
    }

    @Transactional
    public PeliculaResponse update(Long id, PeliculaRequest peliculaRequest) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pelicula no encontrada con ID: " + id));

        peliculaMapper.updateEntityFromRequest(peliculaRequest, pelicula);
        assignDependencies(pelicula, peliculaRequest);

        Pelicula saved = peliculaRepository.save(pelicula);
        return peliculaMapper.toResponse(saved);
    }

    private void assignDependencies(Pelicula pelicula, PeliculaRequest request) {
        if (request.directorId() != null) {
            Director director = directorRepository.findById(request.directorId())
                    .orElseThrow(() -> new RuntimeException("Director no encontrado con ID: " + request.directorId()));
            pelicula.setDirector(director);
        }

        if (request.actorIds() != null && !request.actorIds().isEmpty()) {
            List<Actor> actores = actorRepository.findAllById(request.actorIds());
            if (actores.size() != request.actorIds().size()) {
                throw new RuntimeException("Algunos actores no fueron encontrados");
            }
            pelicula.setActores(new HashSet<>(actores));
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!peliculaRepository.existsById(id)) {
            throw new RuntimeException("Pelicula no encontrada con ID: " + id);
        }
        peliculaRepository.deleteById(id);
    }
}
