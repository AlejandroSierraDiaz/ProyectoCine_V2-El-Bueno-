package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.funcion.FuncionRequest;
import _DAM.Cine_V2.dto.funcion.FuncionResponse;
import _DAM.Cine_V2.modelo.Funcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FuncionMapper {

    @Mapping(target = "peliculaId", source = "pelicula.id")
    @Mapping(target = "salaId", source = "sala.id")
    FuncionResponse toResponse(Funcion funcion);

    @Mapping(target = "pelicula", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @Mapping(target = "entradas", ignore = true)
    @Mapping(target = "id", ignore = true)
    Funcion toEntity(FuncionRequest request);

    @Mapping(target = "pelicula", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @Mapping(target = "entradas", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(FuncionRequest request, @MappingTarget Funcion funcion);
}
