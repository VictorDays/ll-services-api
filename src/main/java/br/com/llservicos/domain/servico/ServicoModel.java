package br.com.llservicos.domain.servico;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServicoModel {

    @Id
    private Long id;

    private String nome;

    private String descricao;
}
