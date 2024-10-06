package br.com.llservicos.domain.usuario.dtos;

import br.com.llservicos.domain.usuario.Perfil;
import br.com.llservicos.domain.usuario.UsuarioModel;

public record UsuarioResponseDTO(
        Long id,
        String telefone,
        Perfil perfil
) {
    public static UsuarioResponseDTO valueOf(UsuarioModel user) {
        return new UsuarioResponseDTO(
                user.getId(),
                user.getTelefone(),
                user.getPerfil()
        );
    }
}