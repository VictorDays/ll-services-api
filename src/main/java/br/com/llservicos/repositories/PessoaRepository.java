package br.com.llservicos.repositories;

import br.com.llservicos.domain.pessoa.PessoaModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaRepository implements PanacheRepository<PessoaModel> {
    public PessoaModel findBytelefone(String telefone) {
        return find("telefone = ?1", telefone).firstResult();
    }

}
