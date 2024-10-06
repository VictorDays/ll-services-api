package br.com.llservicos.domain.usuario.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioDTO(

        @NotBlank(message = "O campo email não pode ser nulo.") String senha,
        @Pattern(regexp = "\\d{10,11}", message = "O número de telefone deve conter apenas dígitos e ter entre 10 e 11 caracteres.")
        @NotBlank(message = "O campo senha não pode ser nulo.") String telefone,
        int perfil) {
}
