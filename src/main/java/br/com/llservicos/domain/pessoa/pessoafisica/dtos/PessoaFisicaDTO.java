package br.com.llservicos.domain.pessoa.pessoafisica.dtos;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class PessoaFisicaDTO{
    @NotBlank(message = "O campo nome não pode ser nulo.")
private String nome;

private String cpf;

private String email;

private String telefone;

private List<EnderecoDTO> enderecos;

// Construtor padrão
public PessoaFisicaDTO() {
}

// Construtor com todos os argumentos
public PessoaFisicaDTO(String nome, String cpf, String email, String telefone, List<EnderecoDTO> enderecos) {
    this.nome = nome;
    this.cpf = cpf;
    this.email = email;
    this.telefone = telefone;
    this.enderecos = enderecos;
}

// Getters e Setters
public String getNome() {
    return nome;
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

// Método toString (opcional, útil para depuração)
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
}