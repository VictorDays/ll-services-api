package br.com.llservicos.domain.pessoa.pessoajuridica.dtos;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.usuario.dtos.UsuarioDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PessoaJuridicaDTO(
        @NotBlank(message = "O campo nome não pode ser nulo.") String nome,
        String cnpj,
        @Email(message = "O e-mail deve ser válido.") String email,
        @NotBlank(message = "O campo Usuário não pode ser nulo.") UsuarioDTO usuario,
        List<EnderecoDTO> enderecos
) {
}
