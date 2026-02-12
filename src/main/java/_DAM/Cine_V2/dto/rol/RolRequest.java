package _DAM.Cine_V2.dto.rol;

import jakarta.validation.constraints.NotBlank;

public record RolRequest(
        @NotBlank(message = "El nombre del rol es obligatorio") String nombre) {
}
