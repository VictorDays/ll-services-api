package br.com.llservicos.domain.pedido;


import java.util.Date;
import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.pessoa.dtos.PessoaDTO;
import br.com.llservicos.domain.servico.ServicoModel;
import jakarta.validation.constraints.NotBlank;

public class PedidoModel {

private long id;
private PessoaModel pessoa;
private Status status;
private ServicoModel servico;
private Date dataservico;
private Double valorTotal;
    
public PedidoModel(long id, PessoaModel pessoa, Status status, ServicoModel servico, Date dataservico, Double valorTotal) {
    this.id = id;
    this.pessoa = pessoa;
    this.status = status;
    this.servico = servico;
    this.dataservico = dataservico;
    this.valorTotal = valorTotal;


}


public long getId() {
    return id;
}

public void setId(long id) {
    this.id = id;
}

public PessoaModel getPessoa() {
    return pessoa;
}

public void setPessoa(@NotBlank(message = "O campo pessoa não pode ser nulo.") PessoaDTO pessoaDTO) {
    this.pessoa = pessoa;
}

public Status getStatus() {
    return status;
}

public void setStatus(@NotBlank(message = "O campo status não pode ser nulo.") String string) {
    this.status = status;
}

public ServicoModel getServico() {
    return servico;
}

public void setServico(ServicoModel servico) {
    this.servico = servico;
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


}