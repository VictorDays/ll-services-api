package br.com.llservicos.repositories;

import br.com.llservicos.domain.pedido.PedidoModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;


@ApplicationScoped
public class PedidoRepository implements PanacheRepository<PedidoModel> {

    public List<PedidoModel> findByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return Collections.emptyList();
        }
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%").list();
    }

    // Buscar pedidos baseados no ID da pessoa
    public List<PedidoModel> findByPessoaId(long pessoaId) {
        return find("SELECT p FROM PedidoModel p WHERE p.pessoa.id = ?1", pessoaId).list();
    }
}
