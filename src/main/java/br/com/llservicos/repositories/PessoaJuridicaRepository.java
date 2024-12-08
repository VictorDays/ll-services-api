package br.com.llservicos.repositories;

import br.com.llservicos.domain.pessoa.pessoajuridica.PessoaJuridicaModel;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaJuridicaRepository implements PanacheRepository<PessoaJuridicaModel> {

    public PanacheQuery<PessoaJuridicaModel> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return find("1=0");  // Returns an empty query result
        }
        String query = "SELECT c FROM Pessoa c JOIN c.pessoa p JOIN p.usuario u WHERE UPPER(u.username) LIKE ?1";
        return find(query, "%" + username.toUpperCase() + "%");
    }
    public PessoaJuridicaModel findByCnpj(String cnpj) {
        String query = "SELECT c FROM PessoaFisicaModel c WHERE c.cpf = ?1";
        return find(query, cnpj).singleResultOptional().orElse(null);
    }

}