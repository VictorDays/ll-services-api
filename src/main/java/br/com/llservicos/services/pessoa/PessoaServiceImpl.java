package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;
import br.com.llservicos.repositories.PessoaRepository;
import br.com.llservicos.repositories.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;

@ApplicationScoped
public class PessoaServiceImpl implements PessoaService{
    @Inject
    PessoaRepository pessoaRepository;
    @Override
    public PessoaResponseDTO login(String telefone, String senha) {

        return null;
    }
}
