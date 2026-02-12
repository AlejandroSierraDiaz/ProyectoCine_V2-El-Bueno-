package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.sala.SalaRequest;
import _DAM.Cine_V2.dto.sala.SalaResponse;
import _DAM.Cine_V2.mapper.SalaMapper;
import _DAM.Cine_V2.modelo.Sala;
import _DAM.Cine_V2.repositorio.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    public List<SalaResponse> findAll() {
        return salaRepository.findAll().stream()
                .map(salaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SalaResponse findById(Long id) {
        return salaRepository.findById(id)
                .map(salaMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada con ID: " + id));
    }

    public SalaResponse create(SalaRequest salaRequest) {
        Sala sala = salaMapper.toEntity(salaRequest);
        Sala saved = salaRepository.save(sala);
        return salaMapper.toResponse(saved);
    }

    public SalaResponse update(Long id, SalaRequest salaRequest) {
        if (!salaRepository.existsById(id)) {
            throw new RuntimeException("Sala no encontrada con ID: " + id);
        }
        Sala sala = salaMapper.toEntity(salaRequest);
        sala.setId(id);
        Sala saved = salaRepository.save(sala);
        return salaMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        if (!salaRepository.existsById(id)) {
            throw new RuntimeException("Sala no encontrada con ID: " + id);
        }
        salaRepository.deleteById(id);
    }
}
