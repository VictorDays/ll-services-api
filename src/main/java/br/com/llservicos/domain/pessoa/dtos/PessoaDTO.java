package br.com.llservicos.domain.pessoa;

import br.com.llservicos.domain.usuario.dtos.UsuarioDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PessoaDTO(
        @NotBlank(message = "O campo nome não pode ser nulo.") String nome,
        @Email(message = "O e-mail deve ser válido.") String email,
        @NotBlank(message = "O campo telefone não pode ser nulo.") UsuarioDTO usuario

        //falta implementar enderecoDTO @Emily

        ) {
}
