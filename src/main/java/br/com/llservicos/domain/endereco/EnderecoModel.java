package br.com.llservicos.domain.endereco;

import jakarta.persistence.*;
import br.com.llservicos.domain.pessoa.PessoaModel;

@Entity
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "endereco_seq", sequenceName = "endereco_sequence", allocationSize = 1)
    private Long id;
    private String logadouro;
    private String complemento;
    private String bairro;
    private Integer numero;
    private String cep;
    private String cidade;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private PessoaModel pessoa;

    public EnderecoModel() {
    }

    public EnderecoModel(String nome, String logadouro, String complemento, String bairro, Integer numero, String cep, String cidade, String estado, PessoaModel pessoa) {

        this.logadouro = logadouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.pessoa = pessoa;
    }

    public PessoaModel getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaModel pessoa) {
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogadouro() {
        return logadouro;
    }

    public void setLogadouro(String logadouro) {
        this.logadouro = logadouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
