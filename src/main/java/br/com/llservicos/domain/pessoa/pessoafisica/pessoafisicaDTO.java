package br.com.llservicos.domain.pessoa.pessoafisica;

import jakarta.validation.constraints.NotBlank;

public record pessoafisicaDTO(
    
    @NotBlank(message = "O nome deve ser informado.") 
    String nome

) {
}