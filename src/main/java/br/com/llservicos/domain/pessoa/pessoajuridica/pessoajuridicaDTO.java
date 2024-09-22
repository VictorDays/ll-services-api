package br.com.llservicos.domain.pessoa.pessoajuridica;

import jakarta.validation.constraints.NotBlank;

public record pessoajuridicaDTO(

    @NotBlank(message = "O nome deve ser informado.") 
    String nome
) {
}
