package br.com.llservicos.repositories;

import java.util.Collections;
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

    // Busca endereços pelo pessoa_id
    public List<EnderecoResponseDTO> findByPessoaId(Long pessoaId) {
        if (pessoaId == null) {
            return null;
        }
        return find("pessoa_id", pessoaId)
                .stream()
                .map(endereco -> new EnderecoResponseDTO(
                        endereco.getId(),
                        endereco.getLogadouro(),
                        endereco.getBairro(),
                        endereco.getNumero(),
                        endereco.getComplemento(),
                        endereco.getCep(),
                        endereco.getCidade(),
                        endereco.getEstado()
                ))
                .toList();
    }
}

