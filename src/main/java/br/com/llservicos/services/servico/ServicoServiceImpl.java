package br.com.llservicos.services.servico;

import java.util.List;
import java.util.Optional;

import br.com.llservicos.domain.servico.ServicoModel;
import br.com.llservicos.repositories.ServicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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
        // Verifica se já existe um serviço com o mesmo nome
        if (servicoRepository.find("nome", servico.getNome()).firstResult() != null) {
            throw new IllegalArgumentException("Já existe um serviço cadastrado com o nome: " + servico.getNome());
        }

        // Caso contrário, persistimos a nova entidade ou atualizamos
        if (servico.getId() != null && servico.getId() > 0) {
            // Atualização de entidade existente
            Optional<ServicoModel> existingServico = servicoRepository.findByIdOptional(servico.getId());
            if (existingServico.isPresent()) {
                ServicoModel toUpdate = existingServico.get();
                toUpdate.setNome(servico.getNome());
                toUpdate.setDescricao(servico.getDescricao());
                return servicoRepository.getEntityManager().merge(toUpdate); // Use merge
            } else {
                throw new IllegalArgumentException("Nenhum serviço encontrado com o ID: " + servico.getId());
            }
        } else {
            // Persistir nova entidade
            servicoRepository.persist(servico);
            return servico;
        }
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
        servicoRepository.deleteById(id);
    }
}
