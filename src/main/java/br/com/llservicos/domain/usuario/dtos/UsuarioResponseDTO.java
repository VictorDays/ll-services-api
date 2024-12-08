package br.com.llservicos.domain.usuario.dtos;

import br.com.llservicos.domain.usuario.UsuarioModel;

public record UsuarioResponseDTO(
        Long id,
        String telefone,
        String perfil
) {
    public static UsuarioResponseDTO valueOf(UsuarioModel user) {
        return new UsuarioResponseDTO(
                user.getId(),
                user.getTelefone(),
                user.getPerfil().getLabel() // Exibe o nome legível
        );
    }
}