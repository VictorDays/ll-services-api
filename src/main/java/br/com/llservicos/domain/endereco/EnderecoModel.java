package br.com.llservicos.domain.endereco;

import jakarta.persistence.*;
import br.com.llservicos.domain.pessoa.PessoaModel;

@Entity
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private PessoaModel pessoa;

    public PessoaModel getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaModel pessoa) {
        this.pessoa = pessoa;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
