package br.com.llservicos.repositories;

import br.com.llservicos.domain.servico.ServicoModel;
import br.com.llservicos.domain.usuario.UsuarioModel;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;


import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ServicoRepository implements PanacheRepository<ServicoModel> {

       public List<ServicoModel> findByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return Collections.emptyList();
        }
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%").list();
    }

    public boolean existsById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo");
    }

        return count("id", id) > 0;
    }

    public ServicoModel findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo");
        }

        return find("id", id).firstResult();
    }

    public ServicoModel save(ServicoModel servico) {
        if (servico == null) {
            throw new IllegalArgumentException("O objeto ServicoModel não pode ser nulo");
    }
        return servico;

    }
}
