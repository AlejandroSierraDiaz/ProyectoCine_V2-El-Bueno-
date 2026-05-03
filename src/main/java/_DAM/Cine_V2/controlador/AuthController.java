package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.auth.LoginRequestDTO;
import _DAM.Cine_V2.dto.auth.LoginResponseDTO;
import _DAM.Cine_V2.dto.auth.RegisterRequestDTO;
import _DAM.Cine_V2.dto.auth.RegisterResponseDTO;
import _DAM.Cine_V2.servicio.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Paso 5: Autenticación (Login) 🆔
 * Punto de entrada público para obtener el Token.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO req) {
        usuarioService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponseDTO(req.email(), "Usuario registrado correctamente"));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<RegisterResponseDTO> registerAdmin(@RequestBody RegisterRequestDTO req) {
        usuarioService.registerAdmin(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponseDTO(req.email(), "Administrador registrado correctamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO req) {
        return ResponseEntity.ok(usuarioService.login(req));
    }
}
