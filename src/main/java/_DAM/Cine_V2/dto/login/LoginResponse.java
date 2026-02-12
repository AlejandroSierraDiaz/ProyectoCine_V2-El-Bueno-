package _DAM.Cine_V2.dto.login;

import _DAM.Cine_V2.dto.usuario.UsuarioResponse;

public record LoginResponse(
        String token,
        UsuarioResponse usuario) {
}
