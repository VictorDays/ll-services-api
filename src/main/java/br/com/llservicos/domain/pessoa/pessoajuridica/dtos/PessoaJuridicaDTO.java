package br.com.llservicos.domain.pessoa.pessoajuridica.dtos;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class PessoaJuridicaDTO {
    @NotBlank(message = "O campo nome não pode ser nulo.")
    private String nome;

    private String cnpj;

    @Email(message = "O e-mail deve ser válido.")
    private String email;

    private String telefone;

    private List<EnderecoDTO> enderecos;

    // Construtor padrão
    public PessoaJuridicaDTO() {
    }

    // Construtor com todos os argumentos
    public PessoaJuridicaDTO(String nome, String cnpj, String email, String telefone, List<EnderecoDTO> enderecos) {
        this.nome = nome;
        this.cnpj = cnpj;
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

    // Método toString (opcional, útil para depuração)
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
}