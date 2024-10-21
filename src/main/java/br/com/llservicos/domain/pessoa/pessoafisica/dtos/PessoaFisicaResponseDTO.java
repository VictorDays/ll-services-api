package br.com.llservicos.domain.pessoa.pessoafisica.dtos;

import br.com.llservicos.domain.endereco.dtos.EnderecoResponseDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.stream.Collectors;

public record PessoaFisicaResponseDTO(
    String nome,
    String email,
    String telefone,
    String cpf,
    List<EnderecoResponseDTO> enderecos
) {
    public static PessoaFisicaResponseDTO valueOf (PessoaFisicaModel pessoaFisica) {
        return new PessoaFisicaResponseDTO(
                pessoaFisica.getNome(),
                pessoaFisica.getEmail(),
                pessoaFisica.getTelefone(),
                pessoaFisica.getCpf(),
                pessoaFisica.getEnderecos().stream()
                        .map(EnderecoResponseDTO::valueOf)
                        .collect(Collectors.toList())
        );
    }

}