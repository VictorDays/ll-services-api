package br.com.llservicos.repositories;

import br.com.llservicos.domain.servico.ServicoModel;
import br.com.llservicos.domain.usuario.UsuarioModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ServicoRepository implements PanacheRepository<ServicoModel> {
    public List<String> listarNome() {
        return getEntityManager().createQuery("SELECT s.nome FROM ServicoModel s ORDER BY s.id ASC", String.class).getResultList();
    }
    public List<String> listarNomeEDescricao() {
        return getEntityManager().createQuery(
                        "SELECT CONCAT(s.nome, ': ', s.descricao) FROM ServicoModel s ORDER BY s.id ASC",
                        String.class)
                .getResultList();
    }
}
