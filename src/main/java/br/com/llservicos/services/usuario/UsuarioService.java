package br.com.llservicos.services.usuario;

import br.com.llservicos.domain.usuario.dtos.UsuarioResponseDTO;

    public interface UsuarioService {
        public UsuarioResponseDTO login(String login, String senha);
    }