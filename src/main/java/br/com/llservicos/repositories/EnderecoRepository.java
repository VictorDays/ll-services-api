package br.com.llservicos.repositories;

import java.util.List;

import br.com.llservicos.domain.endereco.EnderecoModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<EnderecoModel> {

    public List<EnderecoModel> findByCep(String cep) {
        return find("cep", cep).list();
    }

    // Retorna o logradouro do último endereço cadastrado de uma pessoa
    public String ultimoEndereco(Long pessoaId) {
        return find("pessoa.id = ?1 ORDER BY id DESC", pessoaId)
                .firstResultOptional()
                .map(EnderecoModel::getLogadouro)
                .orElse(null);  // Retorna null se não encontrar nenhum endereço
    }
}

