package br.com.llservicos.services.servico;

import br.com.llservicos.domain.servico.ServicoModel;
import br.com.llservicos.repositories.ServicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
    public ServicoModel save(ServicoModel servico) {
        ServicoModel servicoModel = new ServicoModel();
        servicoModel.setNome("Nome de teste");
        servicoModel.setDescricao("Descrição de teste");
        return servicoModel;
//        servicoRepository.persist(servico); TODO - Descomentar após a criação do banco de dados
//        return servico;
    }

    @Override
    public Optional<ServicoModel> findById(Long id) {
        ServicoModel servicoModel = new ServicoModel();
        servicoModel.setNome("Nome de teste");
        servicoModel.setDescricao("Descrição de teste");
        return Optional.of(servicoModel);
//        return servicoRepository.findByIdOptional(id); TODO - Descomentar após a criação do banco de dados
    }

    @Override
    public List<ServicoModel> findAll() {
        ServicoModel servicoModel = new ServicoModel();
        servicoModel.setNome("Nome de teste");
        servicoModel.setDescricao("Descrição de teste");
        List<ServicoModel> servicoModels = List.of(servicoModel);
        return servicoModels;
//        return servicoRepository.listAll(); TODO - Descomentar após a criação do banco de dados
    }

    @Override
    public void deleteById(Long id) {
//        servicoRepository.deleteById(id); TODO - Descomentar após a criação do banco de dados
    }
}
