package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.entrada.EntradaRequest;
import _DAM.Cine_V2.dto.entrada.EntradaResponse;
import _DAM.Cine_V2.modelo.Entrada;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntradaMapper {

    @Mapping(target = "funcionId", source = "funcion.id")
    @Mapping(target = "ventaId", source = "venta.id")
    EntradaResponse toResponse(Entrada entrada);

    @Mapping(target = "funcion", ignore = true)
    @Mapping(target = "venta", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo", ignore = true)
    Entrada toEntity(EntradaRequest request);

    @Mapping(target = "funcion", ignore = true)
    @Mapping(target = "venta", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo", ignore = true)
    void updateEntityFromRequest(EntradaRequest request, @MappingTarget Entrada entrada);
}
