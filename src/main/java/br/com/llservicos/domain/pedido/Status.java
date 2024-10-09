package br.com.llservicos.domain.pedido;

public enum Status {

    PENDENTE("pendente"),
    AGENDADO("Agendado"),
    CONCLUIDO("Concluido");
   

    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
