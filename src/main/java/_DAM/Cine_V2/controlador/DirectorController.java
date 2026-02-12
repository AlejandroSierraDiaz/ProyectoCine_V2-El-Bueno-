package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.director.DirectorRequest;
import _DAM.Cine_V2.dto.director.DirectorResponse;
import _DAM.Cine_V2.servicio.DirectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/directores")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public ResponseEntity<List<DirectorResponse>> findAll() {
        return ResponseEntity.ok(directorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(directorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DirectorResponse> create(@Valid @RequestBody DirectorRequest directorRequest) {
        return new ResponseEntity<>(directorService.create(directorRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorResponse> update(@PathVariable Long id,
            @Valid @RequestBody DirectorRequest directorRequest) {
        return ResponseEntity.ok(directorService.update(id, directorRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        directorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
