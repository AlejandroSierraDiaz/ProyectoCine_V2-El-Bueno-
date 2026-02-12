package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.entrada.EntradaRequest;
import _DAM.Cine_V2.dto.entrada.EntradaResponse;
import _DAM.Cine_V2.servicio.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entradas")
@RequiredArgsConstructor
public class EntradaController {

    private final EntradaService entradaService;

    @GetMapping
    public ResponseEntity<List<EntradaResponse>> findAll() {
        return ResponseEntity.ok(entradaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(entradaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EntradaResponse> create(@Valid @RequestBody EntradaRequest entradaRequest) {
        return new ResponseEntity<>(entradaService.create(entradaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradaResponse> update(@PathVariable Long id,
            @Valid @RequestBody EntradaRequest entradaRequest) {
        return ResponseEntity.ok(entradaService.update(id, entradaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        entradaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
