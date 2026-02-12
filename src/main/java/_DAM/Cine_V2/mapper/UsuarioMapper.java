package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.usuario.UsuarioRequest;
import _DAM.Cine_V2.dto.usuario.UsuarioResponse;
import _DAM.Cine_V2.modelo.Rol;
import _DAM.Cine_V2.modelo.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToStrings")
    UsuarioResponse toResponse(Usuario usuario);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "ventas", ignore = true)
    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioRequest request);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "ventas", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(UsuarioRequest request, @MappingTarget Usuario usuario);

    @Named("mapRolesToStrings")
    default Set<String> mapRolesToStrings(Set<Rol> roles) {
        if (roles == null)
            return Collections.emptySet();
        return roles.stream().map(Rol::getNombre).collect(Collectors.toSet());
    }
}
