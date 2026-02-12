package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.funcion.FuncionRequest;
import _DAM.Cine_V2.dto.funcion.FuncionResponse;
import _DAM.Cine_V2.servicio.FuncionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/funciones")
@RequiredArgsConstructor
public class FuncionController {

    private final FuncionService funcionService;

    @GetMapping
    public ResponseEntity<List<FuncionResponse>> findAll() {
        return ResponseEntity.ok(funcionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(funcionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FuncionResponse> create(@Valid @RequestBody FuncionRequest funcionRequest) {
        return new ResponseEntity<>(funcionService.create(funcionRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionResponse> update(@PathVariable Long id,
            @Valid @RequestBody FuncionRequest funcionRequest) {
        return ResponseEntity.ok(funcionService.update(id, funcionRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        funcionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
