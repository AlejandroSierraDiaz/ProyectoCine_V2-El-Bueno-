package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.actor.ActorRequest;
import _DAM.Cine_V2.dto.actor.ActorResponse;
import _DAM.Cine_V2.mapper.ActorMapper;
import _DAM.Cine_V2.modelo.Actor;
import _DAM.Cine_V2.repositorio.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public List<ActorResponse> findAll() {
        return actorRepository.findAll().stream()
                .map(actorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ActorResponse findById(Long id) {
        return actorRepository.findById(id)
                .map(actorMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Actor no encontrado con ID: " + id));
    }

    public ActorResponse create(ActorRequest actorRequest) {
        Actor actor = actorMapper.toEntity(actorRequest);
        Actor saved = actorRepository.save(actor);
        return actorMapper.toResponse(saved);
    }

    public ActorResponse update(Long id, ActorRequest actorRequest) {
        if (!actorRepository.existsById(id)) {
            throw new RuntimeException("Actor no encontrado con ID: " + id);
        }
        Actor actor = actorMapper.toEntity(actorRequest);
        actor.setId(id);
        Actor saved = actorRepository.save(actor);
        return actorMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        if (!actorRepository.existsById(id)) {
            throw new RuntimeException("Actor no encontrado con ID: " + id);
        }
        actorRepository.deleteById(id);
    }
}
