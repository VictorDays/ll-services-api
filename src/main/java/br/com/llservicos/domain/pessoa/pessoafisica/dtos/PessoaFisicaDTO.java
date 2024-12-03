package br.com.llservicos.domain.pessoa.pessoafisica.dtos;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.usuario.dtos.UsuarioDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

public class PessoaFisicaDTO {
    @NotBlank(message = "O campo nome não pode ser nulo.")
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private List<EnderecoDTO> enderecos;

    public PessoaFisicaDTO(){}

    // Construtor completo
    public PessoaFisicaDTO(String nome, String cpf, String email, String telefone, List<EnderecoDTO> enderecos) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.enderecos = enderecos;
    }

    // Construtor com email nulo
    public PessoaFisicaDTO(String nome, String cpf, String telefone, List<EnderecoDTO> enderecos) {
        this(nome, cpf, null, telefone, enderecos);
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
        PessoaFisicaDTO that = (PessoaFisicaDTO) o;
        return Objects.equals(nome, that.nome) &&
                Objects.equals(cpf, that.cpf) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(enderecos, that.enderecos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cpf, email, telefone, enderecos);
    }

    // toString
    @Override
    public String toString() {
        return "PessoaFisicaDTO{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", enderecos=" + enderecos +
                '}';
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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