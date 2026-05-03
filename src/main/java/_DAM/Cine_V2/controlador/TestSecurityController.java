package _DAM.Cine_V2.controlador;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Paso 6: Autorización (Roles) 👮
 * Comprueba que cada usuario solo entre donde tiene permiso.
 */
@RestController
@RequestMapping("/api/test")
public class TestSecurityController {

    @GetMapping("/info")
    public String getInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Usuario: " + auth.getName() + " | Autoridades: " + auth.getAuthorities();
    }

    @GetMapping("/publico")
    public String publico() {
        return "Acceso público permitido.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_ADMIN')")
    public String soloAdmin() {
        return "Si, efectivamente soy admin y estoy dentro";
    }

    @GetMapping("/usuario")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') or hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public String soloUsuario() {
        return "Si, efectivamente soy usuario y estoy dentro.";
    }
}
