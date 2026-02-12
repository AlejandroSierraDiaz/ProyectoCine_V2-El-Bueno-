package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.venta.VentaRequest;
import _DAM.Cine_V2.dto.venta.VentaResponse;
import _DAM.Cine_V2.modelo.Venta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { EntradaMapper.class })
public interface VentaMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    VentaResponse toResponse(Venta venta);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "importeTotal", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Venta toEntity(VentaRequest request);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "importeTotal", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateEntityFromRequest(VentaRequest request, @MappingTarget Venta venta);
}
