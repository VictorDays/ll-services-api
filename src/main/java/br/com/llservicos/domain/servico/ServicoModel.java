package br.com.llservicos.domain.servico;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servico_seq")
    @SequenceGenerator(name = "servico_seq", sequenceName = "servico_sequence", allocationSize = 1)
    private Long id;

    private String nome;

    private String descricao;
}
