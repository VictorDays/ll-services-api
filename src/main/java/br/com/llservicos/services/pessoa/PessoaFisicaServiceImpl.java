package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.endereco.EnderecoModel;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;
import br.com.llservicos.domain.usuario.Perfil;
import br.com.llservicos.domain.usuario.UsuarioModel;
import br.com.llservicos.repositories.PessoaFisicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PessoaFisicaServiceImpl implements PessoaFisicaService{
    @Inject
    PessoaFisicaRepository fisicaRepository;

    @Override
    @Transactional
    public PessoaFisicaResponseDTO insert(PessoaFisicaDTO dto) throws Exception {
        // Verifique se o cpf já existe
        var pessoa = fisicaRepository.findByCpf(dto.getCpf());

        if (pessoa != null) {
            throw new Exception("CPF já cadastrado!" + pessoa.getClass().getName());
        }

        // Crie um novo
        var person = new PessoaFisicaModel();
        person.setNome(dto.getNome());
        person.setEmail(dto.getEmail());
        person.setCpf(dto.getCpf());
        person.setTelefone(dto.getTelefone());


        // Configure a lista de endereços
        if (dto.getEnderecos() != null && !dto.getEnderecos().isEmpty()) {
            List<EnderecoModel> enderecos = dto.getEnderecos().stream()
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
        return null;
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
