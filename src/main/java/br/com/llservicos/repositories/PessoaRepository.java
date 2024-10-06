package br.com.llservicos.repositories;

import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.usuario.UsuarioModel;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaRepository implements PanacheRepository<PessoaModel> {

}
