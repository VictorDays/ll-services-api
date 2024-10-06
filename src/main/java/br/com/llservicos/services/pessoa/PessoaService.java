package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;

public interface PessoaService {
    public PessoaResponseDTO login(String telefone, String senha);
}
