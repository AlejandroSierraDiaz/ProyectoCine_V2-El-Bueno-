package _DAM.Cine_V2.dto.entrada;

import _DAM.Cine_V2.modelo.EstadoEntrada;
import jakarta.validation.constraints.Min;

public record EntradaDTO(
                Long id,
                String codigo,
                @Min(1) int fila,
                @Min(1) int asiento,
                EstadoEntrada estado,
                Long funcionId,
                Long ventaId) {
}
