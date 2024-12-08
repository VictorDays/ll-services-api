package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.endereco.EnderecoModel;
import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.PessoaJuridicaModel;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaResponseDTO;
import br.com.llservicos.repositories.PessoaJuridicaRepository;
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
public class PessoaJuridicaServiceImpl implements PessoaJuridicaService{
    @Inject
    PessoaJuridicaRepository juridicaRepository;

    @Inject
    EnderecoService enderecoService;

    @Override
    @Transactional
    public PessoaJuridicaResponseDTO insert(PessoaJuridicaDTO dto) throws Exception{
        var juridica = juridicaRepository.findByCnpj(dto.cnpj());

        if (juridica != null) {
            throw new Exception("CNPJ já cadastrado!" + juridica.getClass().getName());
        }

        //Criar novo
        var pJuridica = new PessoaJuridicaModel();
        pJuridica.setNome(dto.nome());
        pJuridica.setEmail(dto.email());
        pJuridica.setCnpj(dto.cnpj());
        pJuridica.setTelefone(dto.telefone());

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

                        endereco.setPessoa(pJuridica); // Aqui define a relação

                        return endereco;
                    })
                    .collect(Collectors.toList());
                    pJuridica.setEnderecos(enderecos);
        } else {
            pJuridica.setEnderecos(Collections.emptyList());
        }

        juridicaRepository.persist(pJuridica);

        return PessoaJuridicaResponseDTO.valueOf(pJuridica);
    }

    @Override
    @Transactional
    public PessoaJuridicaResponseDTO update(PessoaJuridicaDTO dto, Long id) {
        PessoaJuridicaModel pJuridica = juridicaRepository.findById(id);
        pJuridica.setNome(dto.nome());
        pJuridica.setEmail(dto.email());
        pJuridica.setCnpj(dto.cnpj());
        pJuridica.setTelefone(dto.telefone());

        // Configure a lista de endereços
        if (dto.enderecos() != null) {
            for (EnderecoDTO endDTO : dto.enderecos()) {
                // Verifica se o endereço já existe
                Optional<EnderecoModel> enderecoExistente = pJuridica.getEnderecos().stream()
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
        juridicaRepository.persist(pJuridica);
        return PessoaJuridicaResponseDTO.valueOf(pJuridica);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!juridicaRepository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public PessoaJuridicaResponseDTO findById(Long id) {
        PessoaJuridicaModel person = juridicaRepository.findById(id);
        if (person == null) {
            throw new EntityNotFoundException("Pessoa Juridica não encontrado com ID: " + id);
        }
        return PessoaJuridicaResponseDTO.valueOf(person);
    }

    @Override
    public List<PessoaJuridicaResponseDTO> findByAll() {
        return juridicaRepository.listAll().stream()
                .map(e -> PessoaJuridicaResponseDTO.valueOf(e)).toList();
    }
}
