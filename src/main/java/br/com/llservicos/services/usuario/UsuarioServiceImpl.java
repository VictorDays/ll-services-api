package br.com.llservicos.services.usuario;

import br.com.llservicos.controllers.AuthResource;
import br.com.llservicos.domain.usuario.dtos.UsuarioResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.logging.Logger;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOG = Logger.getLogger(String.valueOf(UsuarioServiceImpl.class));

    @Override
    public UsuarioResponseDTO findByLoginAndSenha(String login, String senha) {
        return null;
    }
}
