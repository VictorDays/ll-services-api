package br.com.llservicos.services.servico;

import br.com.llservicos.domain.servico.ServicoModel;
import br.com.llservicos.repositories.ServicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ServicoServiceImpl implements ServicoService {

    private final ServicoRepository servicoRepository;

    @Inject
    public ServicoServiceImpl(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @Override
    @Transactional
    public ServicoModel save(ServicoModel servico) {
        servicoRepository.persist(servico);
        return servico;
    }

    @Override
    public Optional<ServicoModel> findById(Long id) {
        return servicoRepository.findByIdOptional(id);
    }

    @Override
    public List<ServicoModel> findAll() {
        return servicoRepository.listAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
//        servicoRepository.deleteById(id); TODO - Descomentar após a criação do banco de dados
    }
}
