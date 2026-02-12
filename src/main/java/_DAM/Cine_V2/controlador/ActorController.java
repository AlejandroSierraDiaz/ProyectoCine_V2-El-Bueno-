package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.actor.ActorRequest;
import _DAM.Cine_V2.dto.actor.ActorResponse;
import _DAM.Cine_V2.servicio.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actores")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public ResponseEntity<List<ActorResponse>> findAll() {
        return ResponseEntity.ok(actorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(actorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ActorResponse> create(@Valid @RequestBody ActorRequest actorRequest) {
        return new ResponseEntity<>(actorService.create(actorRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorResponse> update(@PathVariable Long id, @Valid @RequestBody ActorRequest actorRequest) {
        return ResponseEntity.ok(actorService.update(id, actorRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
