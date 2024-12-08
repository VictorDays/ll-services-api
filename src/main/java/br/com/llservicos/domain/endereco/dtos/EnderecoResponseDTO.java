package br.com.llservicos.domain.endereco.dtos;

import br.com.llservicos.domain.endereco.EnderecoModel;

public record EnderecoResponseDTO(
    Long id,
    String logradouro,
    String bairro,
    Integer numero,
    String complemento,
    String cep,
    String cidade,
    String estado

) {
    public static EnderecoResponseDTO valueOf(EnderecoModel endereco){
        return new EnderecoResponseDTO(
            endereco.getId(),
            endereco.getLogadouro(),
            endereco.getBairro(),
            endereco.getNumero(), endereco.getComplemento(),
            endereco.getCep(),
            endereco.getCidade(),
            endereco.getEstado()
        );
    }
}