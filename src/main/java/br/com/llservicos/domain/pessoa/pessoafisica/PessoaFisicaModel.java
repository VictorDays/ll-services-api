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

    private String cpf;
    
}
