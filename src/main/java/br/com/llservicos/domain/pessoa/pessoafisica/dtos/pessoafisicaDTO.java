package br.com.llservicos.domain.pessoa.pessoafisica.dtos;

import jakarta.validation.constraints.NotBlank;

public record pessoafisicaDTO(
    
    @NotBlank(message = "O nome deve ser informado.") 
    String nome

) {
}