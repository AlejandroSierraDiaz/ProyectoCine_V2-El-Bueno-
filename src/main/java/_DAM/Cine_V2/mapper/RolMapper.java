package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.rol.RolRequest;
import _DAM.Cine_V2.dto.rol.RolResponse;
import _DAM.Cine_V2.modelo.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RolMapper {
    @Mapping(target = "id", ignore = true)
    Rol toEntity(RolRequest request);

    RolResponse toResponse(Rol rol);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(RolRequest request, @MappingTarget Rol rol);
}
