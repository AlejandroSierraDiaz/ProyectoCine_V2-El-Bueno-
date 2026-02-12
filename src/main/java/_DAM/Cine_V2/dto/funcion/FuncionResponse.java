package _DAM.Cine_V2.dto.funcion;

import java.time.LocalDateTime;

public record FuncionResponse(
        Long id,
        LocalDateTime fechaHora,
        double precio,
        Long peliculaId,
        Long salaId) {
}
