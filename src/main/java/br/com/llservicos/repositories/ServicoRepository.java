package br.com.llservicos.repositories;

import java.util.Collections;
import java.util.List;

import br.com.llservicos.domain.servico.ServicoModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServicoRepository implements PanacheRepository<ServicoModel> {
    public List<ServicoModel> findByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return Collections.emptyList();
        }
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%").list();
    }
}
