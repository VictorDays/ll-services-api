package br.com.llservicos.domain.servico.dto;

import br.com.llservicos.domain.servico.ServicoModel;

public record ServicoResponseDTO(
        String nome,
        String descricao
) {
    public static ServicoResponseDTO valueOf(ServicoModel servico) {
        return new ServicoResponseDTO(
                servico.getNome(),
                servico.getDescricao()
        );
    }
}