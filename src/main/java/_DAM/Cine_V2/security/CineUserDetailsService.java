package _DAM.Cine_V2.security;

import _DAM.Cine_V2.modelo.Rol;
import _DAM.Cine_V2.modelo.Usuario;
import _DAM.Cine_V2.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CineUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Encontrar el usuario por email.
        Usuario u = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // Convertir el modelo Usuario de nuestra DB a lo que Spring Security entiende.
        return User.builder()
                .username(u.getEmail())
                .password(u.getPassword())
                // .roles(...) añade automáticamente el prefijo ROLE_
                // Si el nombre del rol ya lo incluye, podrías usar authorities() en su lugar.
                .roles(u.getRoles().stream()
                        .map(Rol::getNombre)
                        .toArray(String[]::new))
                .build();
    }
}
