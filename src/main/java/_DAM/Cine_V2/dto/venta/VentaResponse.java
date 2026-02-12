package _DAM.Cine_V2.dto.venta;

import _DAM.Cine_V2.dto.entrada.EntradaResponse;
import java.time.LocalDateTime;
import java.util.Set;

public record VentaResponse(
        Long id,
        LocalDateTime fecha,
        double importeTotal,
        String metodoPago,
        String estado,
        Set<EntradaResponse> entradas,
        Long usuarioId) {
}
