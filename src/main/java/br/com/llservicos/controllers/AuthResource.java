package br.com.llservicos.controllers;

import br.com.llservicos.config.GenerationToken.Hash.HashService;
import br.com.llservicos.config.GenerationToken.Jwt.JwtService;
import br.com.llservicos.domain.usuario.dtos.UsuarioDTO;
import br.com.llservicos.domain.usuario.dtos.UsuarioResponseDTO;
import br.com.llservicos.services.usuario.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.logging.Logger;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    UsuarioService usuarioService;
    @Inject
    HashService hashService;
    @Inject
    JwtService jwtService;
    private static final Logger LOG = Logger.getLogger(String.valueOf(AuthResource.class));

    @POST
    public Response login(@Valid UsuarioDTO dto) {

        LOG.info("Iniciando a autenticacao endpoint");

        String hashSenha = hashService.getHashSenha(dto.senha());
        LOG.info("Hash da senha gerado ");

        UsuarioResponseDTO result = usuarioService.login(dto.telefone(), hashSenha);

        if (result != null) {
            LOG.info("Login e senha corretos.");
            String token = jwtService.generateJwt(result);
            LOG.info("Token JWT gerado com sucesso.");

            // Retornar o token JWT na resposta
            return Response.ok(result)
                    .header("Authorization", "Bearer " + token)
                    .build();
        } else {
            LOG.info("Login e senha incorretos.");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
