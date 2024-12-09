package br.com.llservicos.domain.servico.dto;

import br.com.llservicos.domain.servico.ServicoModel;

public record ServicoResponseDTO(
        Long id,
        String nome,
        String descricao
) {
    public static ServicoResponseDTO valueOf(ServicoModel servico) {
        return new ServicoResponseDTO(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao()
        );
    }
}