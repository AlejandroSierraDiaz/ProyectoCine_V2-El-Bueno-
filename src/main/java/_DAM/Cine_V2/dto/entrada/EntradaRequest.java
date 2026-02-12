package _DAM.Cine_V2.dto.entrada;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import _DAM.Cine_V2.modelo.EstadoEntrada;

public record EntradaRequest(
        @Min(1) int fila,
        @Min(1) int asiento,
        EstadoEntrada estado,
        @NotNull Long funcionId) {
}
