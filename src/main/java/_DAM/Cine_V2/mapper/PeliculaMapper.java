package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.pelicula.PeliculaRequest;
import _DAM.Cine_V2.dto.pelicula.PeliculaResponse;
import _DAM.Cine_V2.modelo.Actor;
import _DAM.Cine_V2.modelo.Pelicula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PeliculaMapper {

    @Mapping(target = "directorId", source = "director.id")
    @Mapping(target = "actorIds", source = "actores", qualifiedByName = "mapActorsToIds")
    public abstract PeliculaResponse toResponse(Pelicula pelicula);

    @Mapping(target = "director", ignore = true)
    @Mapping(target = "actores", ignore = true)
    @Mapping(target = "funciones", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract Pelicula toEntity(PeliculaRequest request);

    @Mapping(target = "director", ignore = true)
    @Mapping(target = "actores", ignore = true)
    @Mapping(target = "funciones", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract void updateEntityFromRequest(PeliculaRequest request, @MappingTarget Pelicula pelicula);

    @Named("mapActorsToIds")
    public Set<Long> mapActorsToIds(Set<Actor> actors) {
        if (actors == null)
            return Collections.emptySet();
        return actors.stream().map(Actor::getId).collect(Collectors.toSet());
    }
}
