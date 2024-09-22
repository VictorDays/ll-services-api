package br.com.llservicos.domain.pessoa;

import jakarta.validation.constraints.NotBlank;

public record PessoaDTO(
        @NotBlank(message = "O campo nome não pode ser nulo.") String nome,
        String email,
        @NotBlank(message = "O campo telefone não pode ser nulo.") String telefone) {
}
