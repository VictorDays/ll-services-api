package br.com.llservicos.repositories;

import java.util.List;

import br.com.llservicos.domain.endereco.EnderecoModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public class EnderecoRepository implements PanacheRepository<EnderecoModel> {

    public List<EnderecoModel> findByCep(String cep) {
        return find("cep", cep).list();
    }
    
}
