package br.com.llservicos.repositories;

import br.com.llservicos.domain.usuario.UsuarioModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioModel> {

    public List<UsuarioModel> findByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return Collections.emptyList();
        }
        return find("UPPER(nome) LIKE UPPER(?1)", "%" + nome + "%").list();
    }

    public Optional<UsuarioModel> login(String telefone, String senha) {
        return find("telefone = ?1 and senha = ?2", telefone, senha).firstResultOptional();
    }
}
