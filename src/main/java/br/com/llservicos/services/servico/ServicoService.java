package br.com.llservicos.services.servico;

import br.com.llservicos.domain.servico.ServicoModel;

import java.util.List;
import java.util.Optional;

public interface ServicoService {

    ServicoModel save(ServicoModel servico);

    Optional<ServicoModel> findById(Long id);

    List<ServicoModel> findAll();

    void deleteById(Long id);
}
