package br.com.llservicos.domain.pedido;

import java.util.Date;

import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.servico.ServicoModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Status status;
    private Date dataservico;
    private Double valorTotal;


    @OneToOne
    private PessoaModel pessoa;

    @ManyToOne // Relacionamento muitos-para-um com ServiçoModel
    @JoinColumn(name = "servico_id", nullable = false) // Chave estrangeira
    private ServicoModel servico;

    // Construtor sem parâmetros (necessário para o Hibernate)
    public PedidoModel() {
    }

    public PedidoModel(long id, Status status, Date dataservico, Double valorTotal) {
        this.id = id;
        this.status = status;
        this.dataservico = dataservico;
        this.valorTotal = valorTotal;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "O campo status não pode ser nulo.") String string) {
        // Convertendo a string para maiúsculas e fazendo a correspondência com as
        // descrições
        for (Status s : Status.values()) {
            if (s.getDescricao().equalsIgnoreCase(string)) {
                this.status = s; // Atribui o valor do enum correspondente
                return;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + string); // Se não encontrar um status correspondente
    }

    public Date getDataservico() {
        return dataservico;
    }

    public void setDataservico(Date dataservico) {
        this.dataservico = dataservico;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public ServicoModel getServico() {
        return servico;
    }

    public void setServico(ServicoModel servico) {
        this.servico = servico;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PessoaModel getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaModel pessoa) {
        this.pessoa = pessoa;
    }

}