package br.com.llservicos.domain.pessoa.pessoafisica;

import br.com.llservicos.domain.pessoa.PessoaModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PessoaFisicaModel extends PessoaModel {

    /* @JoinColumn(name = "id_pessoa", referencedColumnName = "id", unique = true)
    private PessoaModel pessoa; */

    private String cpf;
    
}
