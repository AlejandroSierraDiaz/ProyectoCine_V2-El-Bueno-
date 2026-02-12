package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.director.DirectorRequest;
import _DAM.Cine_V2.dto.director.DirectorResponse;
import _DAM.Cine_V2.modelo.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DirectorMapper {
    @Mapping(target = "peliculas", ignore = true)
    @Mapping(target = "id", ignore = true)
    Director toEntity(DirectorRequest request);

    DirectorResponse toResponse(Director director);

    @Mapping(target = "peliculas", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(DirectorRequest request, @MappingTarget Director director);
}
