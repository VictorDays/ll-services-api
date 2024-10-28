package br.com.llservicos.domain.endereco.dtos;
import jakarta.validation.constraints.NotBlank;


public record EnderecoDTO(
        String logradouro,
        String complemento,
        String bairro,
        Integer numero,
        String cep,
        String cidade,
        String estado) {
}