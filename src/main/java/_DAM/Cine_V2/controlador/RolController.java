package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.rol.RolRequest;
import _DAM.Cine_V2.dto.rol.RolResponse;
import _DAM.Cine_V2.servicio.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolResponse>> findAll() {
        return ResponseEntity.ok(rolService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RolResponse> create(@Valid @RequestBody RolRequest rolRequest) {
        return new ResponseEntity<>(rolService.create(rolRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponse> update(@PathVariable Long id, @Valid @RequestBody RolRequest rolRequest) {
        return ResponseEntity.ok(rolService.update(id, rolRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
