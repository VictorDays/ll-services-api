package br.com.llservicos.repositories;

import br.com.llservicos.domain.usuario.UsuarioModel;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioModel> {

    public List<UsuarioModel> findByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return Collections.emptyList();
        }
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%").list();
    }

    public PanacheQuery<UsuarioModel> findByLogin(String login) {
        if (login == null || login.isEmpty()) {
            return null;
        }
        return find("login = ?1", login);
    }

    public PanacheQuery<UsuarioModel> findByLoginAndSenha(String login, String senha) {
        if (login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
            return null;
        }
        return find("login = ?1 AND senha = ?2", login, senha);
    }
}