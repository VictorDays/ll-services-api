package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.endereco.EnderecoModel;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import br.com.llservicos.domain.pessoa.pessoajuridica.PessoaJuridicaModel;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaResponseDTO;
import br.com.llservicos.domain.usuario.Perfil;
import br.com.llservicos.domain.usuario.UsuarioModel;
import br.com.llservicos.repositories.PessoaJuridicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PessoaJuridicaServiceImpl implements PessoaJuridicaService{
    @Inject
    PessoaJuridicaRepository juridicaRepository;

    @Override
    @Transactional
    public PessoaJuridicaResponseDTO insert(PessoaJuridicaDTO dto) throws Exception{


        var person = new PessoaJuridicaModel();
        person.setNome(dto.getNome());
        person.setEmail(dto.getEmail());
        person.setCnpj(dto.getCnpj());
        person.setTelefone(dto.getTelefone());

        // Configure a lista de endereços
        if (dto.getCnpj() != null && !dto.getEnderecos().isEmpty()) {
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

        juridicaRepository.persist(person);

        return PessoaJuridicaResponseDTO.valueOf(person);
    }

    @Override
    @Transactional
    public PessoaJuridicaResponseDTO update(PessoaJuridicaDTO dto, Long id) {
        return null;
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
