package br.com.llservicos.domain.pedido;

public enum Status {

    PENDENTE("pendente",1),
    AGENDADO("Agendado",2),
    CONCLUIDO("Concluido",3);
   

    private String descricao;
    private int numero;

    Status(String descricao, int numero) {

        this.descricao = descricao;
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNumero() {
        return numero;
    }

}
