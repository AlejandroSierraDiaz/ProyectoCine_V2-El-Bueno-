package _DAM.Cine_V2.dto.venta;

import _DAM.Cine_V2.dto.entrada.EntradaRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record VentaRequest(
        @NotBlank(message = "El método de pago es obligatorio") String metodoPago,
        Set<EntradaRequest> entradas,
        @NotNull(message = "El usuario es obligatorio") Long usuarioId) {
}
