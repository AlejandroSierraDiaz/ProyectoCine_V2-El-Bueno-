package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.pelicula.PeliculaRequest;
import _DAM.Cine_V2.dto.pelicula.PeliculaResponse;
import _DAM.Cine_V2.servicio.PeliculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/peliculas")
@RequiredArgsConstructor
public class PeliculaController {

    private final PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<List<PeliculaResponse>> findAll() {
        return ResponseEntity.ok(peliculaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(peliculaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PeliculaResponse> create(@Valid @RequestBody PeliculaRequest peliculaRequest) {
        return new ResponseEntity<>(peliculaService.create(peliculaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaResponse> update(@PathVariable Long id,
            @Valid @RequestBody PeliculaRequest peliculaRequest) {
        return ResponseEntity.ok(peliculaService.update(id, peliculaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        peliculaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
