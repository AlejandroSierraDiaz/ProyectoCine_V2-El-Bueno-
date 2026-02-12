package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.sala.SalaRequest;
import _DAM.Cine_V2.dto.sala.SalaResponse;
import _DAM.Cine_V2.modelo.Sala;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SalaMapper {
    @Mapping(target = "funciones", ignore = true)
    @Mapping(target = "id", ignore = true)
    Sala toEntity(SalaRequest request);

    SalaResponse toResponse(Sala sala);

    @Mapping(target = "funciones", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(SalaRequest request, @MappingTarget Sala sala);
}
