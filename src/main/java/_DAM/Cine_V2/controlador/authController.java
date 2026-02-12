package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.login.LoginRequest;
import _DAM.Cine_V2.dto.login.LoginResponseDTO;
import _DAM.Cine_V2.modelo.Usuario;
import _DAM.Cine_V2.servicio.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class authController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioService.login(loginRequest);

        return ResponseEntity.ok(
                new LoginResponseDTO(usuario.getEmail(), "Login exitoso (Inseguro)"));
    }
}
