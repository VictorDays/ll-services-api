package br.com.llservicos.domain.pessoa.pessoajuridica.dtos;

import br.com.llservicos.domain.endereco.dtos.EnderecoResponseDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.PessoaJuridicaModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.stream.Collectors;

public record PessoaJuridicaResponseDTO(
        String nome,
        String email,
        String telefone,
        String cnpj,
        List<EnderecoResponseDTO> enderecos
) {
    public static PessoaJuridicaResponseDTO valueOf (PessoaJuridicaModel juridicaModel) {
        return new PessoaJuridicaResponseDTO(
                juridicaModel.getNome(),
                juridicaModel.getEmail(),
                juridicaModel.getTelefone(),
                juridicaModel.getCnpj(),
                juridicaModel.getEnderecos().stream()
                        .map(EnderecoResponseDTO::valueOf)
                        .collect(Collectors.toList())
        );
    }
}