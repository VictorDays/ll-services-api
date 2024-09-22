package br.com.llservicos.config.GenerationToken.Jwt;


import br.com.llservicos.domain.usuario.dtos.UsuarioResponseDTO;

public interface JwtService {
    public String generateJwt(UsuarioResponseDTO dto);
}