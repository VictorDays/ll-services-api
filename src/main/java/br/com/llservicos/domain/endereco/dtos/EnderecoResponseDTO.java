package br.com.llservicos.domain.endereco.dtos;

import br.com.llservicos.domain.endereco.EnderecoModel;

public record EnderecoResponseDTO(
    Long id,
    String nome,
    String logradouro,
    String complemento,
    String bairro,
    Integer numero,
    String cep,
    String cidade,
    String estado

) {
    public static EnderecoResponseDTO valueOf(EnderecoModel endereco){
        return new EnderecoResponseDTO(
            endereco.getId(),
            endereco.getNome(),
            endereco.getLogadouro(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getNumero(),
            endereco.getCep(),
            endereco.getCidade(),
            endereco.getEstado()
        );
    }
}