package br.com.llservicos.repositories;

import br.com.llservicos.domain.servico.ServicoModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServicoRepository implements PanacheRepository<ServicoModel> {
}
