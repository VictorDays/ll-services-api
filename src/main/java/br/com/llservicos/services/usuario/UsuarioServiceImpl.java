package br.com.llservicos.services.usuario;

import br.com.llservicos.domain.usuario.UsuarioModel;
import br.com.llservicos.domain.usuario.dtos.UsuarioResponseDTO;
import br.com.llservicos.repositories.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponseDTO login(String telefone, String senha) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.login(telefone, senha);

        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            // Converter UsuarioModel para UsuarioResponseDTO
            return new UsuarioResponseDTO(usuario.getId(), usuario.getTelefone(), usuario.getPerfil());
        }

        return null; // Login falhou
    }
    }