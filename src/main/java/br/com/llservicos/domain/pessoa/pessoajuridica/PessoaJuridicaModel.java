package br.com.llservicos.domain.pessoa.pessoajuridica;

import br.com.llservicos.domain.pessoa.PessoaModel;
import jakarta.persistence.JoinColumn;

public class PessoaJuridicaModel {

    @JoinColumn(name = "id_pessoa", referencedColumnName = "id", unique = true)
    private PessoaModel pessoa;

    private String cpf;


    public PessoaModel getPessoa() {
        return pessoa;
    }
    public void setPessoa(PessoaModel pessoa) {
        this.pessoa = pessoa;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}



