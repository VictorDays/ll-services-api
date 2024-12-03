package br.com.llservicos.domain.pessoa.pessoajuridica.dtos;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

public class PessoaJuridicaDTO {
    @NotBlank(message = "O campo nome não pode ser nulo.")
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
    private List<EnderecoDTO> enderecos;

    public PessoaJuridicaDTO(){}

    // Construtor completo
    public PessoaJuridicaDTO(String nome, String cnpj, String email, String telefone, List<EnderecoDTO> enderecos) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.enderecos = enderecos;
    }

    // Construtor com email nulo
    public PessoaJuridicaDTO(String nome, String cnpj, String telefone, List<EnderecoDTO> enderecos) {
        this(nome, cnpj, null, telefone, enderecos);
    }

    // Getters
    public String getNome() {
        return nome;
    }

    // ... outros getters ...

    // Equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PessoaJuridicaDTO that = (PessoaJuridicaDTO) o;
        return Objects.equals(nome, that.nome) &&
                Objects.equals(cnpj, that.cnpj) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(enderecos, that.enderecos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cnpj, email, telefone, enderecos);
    }

    // toString
    @Override
    public String toString() {
        return "PessoaJuridicaDTO{" +
                "nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", enderecos=" + enderecos +
                '}';
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }
}