package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.sala.SalaRequest;
import _DAM.Cine_V2.dto.sala.SalaResponse;
import _DAM.Cine_V2.servicio.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaResponse>> findAll() {
        return ResponseEntity.ok(salaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SalaResponse> create(@Valid @RequestBody SalaRequest salaRequest) {
        return new ResponseEntity<>(salaService.create(salaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponse> update(@PathVariable Long id, @Valid @RequestBody SalaRequest salaRequest) {
        return ResponseEntity.ok(salaService.update(id, salaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
