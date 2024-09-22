package br.com.llservicos.domain.usuario.dtos;

import br.com.llservicos.domain.usuario.Perfil;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Perfil perfil
) { }