package br.com.llservicos.domain.pessoa.dtos;


import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.usuario.dtos.UsuarioResponseDTO;

public record PessoaResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone

        //Falta enderecoResponseDTO @Emily
) {
    public static PessoaResponseDTO valueOf(PessoaModel pessoa) {
        return new PessoaResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getTelefone()
        );
    }
}
