package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.endereco.EnderecoModel;
import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;
import br.com.llservicos.repositories.PessoaFisicaRepository;
import br.com.llservicos.services.endereco.EnderecoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PessoaFisicaServiceImpl implements PessoaFisicaService {
    @Inject
    PessoaFisicaRepository fisicaRepository;

    @Inject
    EnderecoService enderecoService;

    @Override
    @Transactional
    public PessoaFisicaResponseDTO insert(PessoaFisicaDTO dto) throws Exception {
        // Verifique se o cpf já existe
        var pessoa = fisicaRepository.findByCpf(dto.cpf());

        if (pessoa != null) {
            throw new Exception("CPF já cadastrado!" + pessoa.getClass().getName());
        }

        // Crie um novo
        var person = new PessoaFisicaModel();
        person.setNome(dto.nome());
        person.setEmail(dto.email());
        person.setTelefone(dto.telefone());
        person.setCpf(dto.cpf());

        // Configure a lista de endereços
        if (dto.enderecos() != null && !dto.enderecos().isEmpty()) {
            List<EnderecoModel> enderecos = dto.enderecos().stream()
                    .map(end -> {
                        var endereco = new EnderecoModel();
                        endereco.setCep(end.cep());
                        endereco.setBairro(end.bairro());
                        endereco.setNumero(end.numero());
                        endereco.setLogadouro(end.logradouro());
                        endereco.setComplemento(end.complemento());
                        endereco.setCidade(end.cidade());
                        endereco.setEstado(end.estado());

                        endereco.setPessoa(person); // Aqui define a relação
                        return endereco;
                    })
                    .collect(Collectors.toList());
            person.setEnderecos(enderecos);
        } else {
            person.setEnderecos(Collections.emptyList());
        }

        // Persista o novo cliente
        fisicaRepository.persist(person);

        return PessoaFisicaResponseDTO.valueOf(person);
    }

    @Override
    @Transactional
    public PessoaFisicaResponseDTO update(PessoaFisicaDTO dto, Long id) {
        PessoaFisicaModel pessoa = fisicaRepository.findById(id);
        pessoa.setNome(dto.nome());
        pessoa.setEmail(dto.email());
        pessoa.setTelefone(dto.telefone());
        pessoa.setCpf(dto.cpf());

        // Configure a lista de endereços
        if (dto.enderecos() != null) {
            for (EnderecoDTO endDTO : dto.enderecos()) {
                // Verifica se o endereço já existe
                Optional<EnderecoModel> enderecoExistente = pessoa.getEnderecos().stream()
                        .filter(end -> end.getLogadouro().equals(endDTO.logradouro()) &&
                                end.getNumero().equals(endDTO.numero()))
                        .findFirst();
                        
                if (enderecoExistente.isPresent()) {
                    // Atualiza o endereço existente
                    EnderecoModel endereco = enderecoExistente.get();
                    endereco.setLogadouro(endDTO.logradouro());
                } else {
                    // Cria um novo endereço
                    enderecoService.adicionaEndBot(id, endDTO);
                }
            }
        }

        // Persista o novo cliente
        fisicaRepository.persist(pessoa);
        return PessoaFisicaResponseDTO.valueOf(pessoa);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!fisicaRepository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public PessoaFisicaResponseDTO findById(Long id) {
        PessoaFisicaModel person = fisicaRepository.findById(id);
        if (person == null) {
            throw new EntityNotFoundException("Pessoa Fisica não encontrado com ID: " + id);
        }
        return PessoaFisicaResponseDTO.valueOf(person);
    }

    @Override
    public List<PessoaFisicaResponseDTO> findByAll() {
        return fisicaRepository.listAll().stream()
                .map(e -> PessoaFisicaResponseDTO.valueOf(e)).toList();
    }
}
