package br.com.llservicos.domain.endereco.dto;
import jakarta.validation.constraints.NotBlank;


public record EnderecoDTO(
        @NotBlank(message = "O campo nome não pode ser nulo.") String nome,
        @NotBlank(message = "O campo logradouro não pode ser nulo.") String logradouro,
        String complemento,
        @NotBlank(message = "O campo bairro não pode ser nulo.") String bairro,
        Integer numero,
        @NotBlank(message = "O campo CEP não pode ser nulo.") String cep,
        @NotBlank(message = "O campo cidade não pode ser nulo.") String cidade,
        @NotBlank(message = "O campo estado não pode ser nulo.") String estado) {
}