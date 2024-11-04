package br.com.llservicos.domain.servico.dto;
import jakarta.validation.constraints.NotBlank;

public record ServicoDTO(
        @NotBlank(message = "Por favor, preencha o campo nome.") String nome,
        @NotBlank(message = "Por favor, preencha o campo descrição.") String descricao) {
}