package br.com.llservicos.services.servico;

import java.util.List;
import java.util.Optional;

import br.com.llservicos.domain.servico.ServicoModel;

public interface ServicoService {

    ServicoModel save(ServicoModel servico);

    Optional<ServicoModel> findById(Long id);

    List<ServicoModel> findAll();

    void deleteById(Long id);
}
