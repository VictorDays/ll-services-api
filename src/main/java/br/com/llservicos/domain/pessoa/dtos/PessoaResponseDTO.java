package br.com.llservicos.domain.pessoa;


import br.com.llservicos.domain.usuario.UsuarioModel;
import br.com.llservicos.domain.usuario.dtos.UsuarioResponseDTO;

public record PessoaResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        UsuarioResponseDTO usuario

        //Falta enderecoResponseDTO @Emily
) {
    public static PessoaResponseDTO valueOf(PessoaModel pessoa) {
        return new PessoaResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getUsuario().getTelefone(),
                UsuarioResponseDTO.
        );
    }
}
