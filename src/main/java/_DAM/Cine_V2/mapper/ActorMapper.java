package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.actor.ActorRequest;
import _DAM.Cine_V2.dto.actor.ActorResponse;
import _DAM.Cine_V2.modelo.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActorMapper {
    @Mapping(target = "peliculas", ignore = true)
    @Mapping(target = "id", ignore = true)
    Actor toEntity(ActorRequest request);

    ActorResponse toResponse(Actor actor);

    @Mapping(target = "peliculas", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(ActorRequest request, @MappingTarget Actor actor);
}
