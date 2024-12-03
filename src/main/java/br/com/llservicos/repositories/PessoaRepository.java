package br.com.llservicos.repositories;

import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.pessoa.dtos.PessoaDTO;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.PessoaJuridicaModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class PessoaRepository implements PanacheRepository<PessoaModel> {
    public PessoaModel findBytelefone(String telefone) {
        return find("telefone = ?1", telefone).firstResult();
    }

}
