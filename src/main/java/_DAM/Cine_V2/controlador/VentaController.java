package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.venta.VentaRequest;
import _DAM.Cine_V2.dto.venta.VentaResponse;
import _DAM.Cine_V2.servicio.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponse>> findAll() {
        return ResponseEntity.ok(ventaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VentaResponse> create(@Valid @RequestBody VentaRequest ventaRequest) {
        return new ResponseEntity<>(ventaService.create(ventaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponse> update(@PathVariable Long id, @Valid @RequestBody VentaRequest ventaRequest) {
        return ResponseEntity.ok(ventaService.update(id, ventaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ventaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
