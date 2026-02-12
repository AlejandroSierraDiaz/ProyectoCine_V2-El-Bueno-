package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.director.DirectorRequest;
import _DAM.Cine_V2.dto.director.DirectorResponse;
import _DAM.Cine_V2.mapper.DirectorMapper;
import _DAM.Cine_V2.modelo.Director;
import _DAM.Cine_V2.repositorio.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    public List<DirectorResponse> findAll() {
        return directorRepository.findAll().stream()
                .map(directorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DirectorResponse findById(Long id) {
        return directorRepository.findById(id)
                .map(directorMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Director no encontrado con ID: " + id));
    }

    public DirectorResponse create(DirectorRequest directorRequest) {
        Director director = directorMapper.toEntity(directorRequest);
        Director saved = directorRepository.save(director);
        return directorMapper.toResponse(saved);
    }

    public DirectorResponse update(Long id, DirectorRequest directorRequest) {
        if (!directorRepository.existsById(id)) {
            throw new RuntimeException("Director no encontrado con ID: " + id);
        }
        Director director = directorMapper.toEntity(directorRequest);
        director.setId(id);
        Director saved = directorRepository.save(director);
        return directorMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        if (!directorRepository.existsById(id)) {
            throw new RuntimeException("Director no encontrado con ID: " + id);
        }
        directorRepository.deleteById(id);
    }
}
