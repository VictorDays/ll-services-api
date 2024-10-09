package br.com.llservicos.repositories;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.usuario.UsuarioModel;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;


import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<PedidoModel> {

       public List<PedidoModel> findByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return Collections.emptyList();
        }
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%").list();
    }
}
