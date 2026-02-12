package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.rol.RolRequest;
import _DAM.Cine_V2.dto.rol.RolResponse;
import _DAM.Cine_V2.mapper.RolMapper;
import _DAM.Cine_V2.modelo.Rol;
import _DAM.Cine_V2.repositorio.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public List<RolResponse> findAll() {
        return rolRepository.findAll().stream()
                .map(rolMapper::toResponse)
                .collect(Collectors.toList());
    }

    public RolResponse findById(Long id) {
        return rolRepository.findById(id)
                .map(rolMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }

    public RolResponse create(RolRequest rolRequest) {
        Rol rol = rolMapper.toEntity(rolRequest);
        Rol saved = rolRepository.save(rol);
        return rolMapper.toResponse(saved);
    }

    public RolResponse update(Long id, RolRequest rolRequest) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }
        Rol rol = rolMapper.toEntity(rolRequest);
        rol.setId(id);
        Rol saved = rolRepository.save(rol);
        return rolMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }
        rolRepository.deleteById(id);
    }
}
