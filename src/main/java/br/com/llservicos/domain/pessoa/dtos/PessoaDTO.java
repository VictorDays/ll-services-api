package br.com.llservicos.domain.pessoa.dtos;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.usuario.dtos.UsuarioDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PessoaDTO(
        @NotBlank(message = "O campo nome não pode ser nulo.") String nome,
        @Email(message = "O e-mail deve ser válido.") String email,
        @NotBlank(message = "O campo Usuário não pode ser nulo.") UsuarioDTO usuario,
        String telefone,
        EnderecoDTO endereco

        ) {
}
