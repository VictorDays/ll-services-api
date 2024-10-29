package br.com.llservicos.repositories;

import java.util.List;

import br.com.llservicos.domain.endereco.EnderecoModel;
import br.com.llservicos.domain.endereco.dtos.EnderecoResponseDTO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<EnderecoModel> {

    public List<EnderecoModel> findByCep(String cep) {
        return find("cep", cep).list();
    }
}

