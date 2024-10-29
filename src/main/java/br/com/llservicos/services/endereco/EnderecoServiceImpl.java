package br.com.llservicos.services.endereco;
import java.util.List;

import br.com.llservicos.domain.endereco.EnderecoModel;
import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.endereco.dtos.EnderecoResponseDTO;
import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.repositories.EnderecoRepository;
import br.com.llservicos.repositories.PessoaFisicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {


    @Inject
    PessoaFisicaRepository pessoaRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    //@Inject
    //PessoaService pessoaService;



    @Override
    @Transactional
    public EnderecoResponseDTO insert(@Valid EnderecoDTO enderecoDTO) {

        EnderecoModel endereco = new EnderecoModel();
        endereco.setBairro(enderecoDTO.bairro());
        endereco.setCep(enderecoDTO.cep());
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setComplemento(enderecoDTO.complemento());
        endereco.setEstado(enderecoDTO.estado());
        endereco.setLogadouro(enderecoDTO.logradouro());


        enderecoRepository.persist(endereco);

        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(String nome, Long idEndereco, Long idPessoa, @Valid EnderecoDTO enderecoDTO) {
        EnderecoModel endereco = enderecoRepository.findById(idEndereco);
        if(endereco == null){
            throw new NotFoundException("Endereço não encontrado.");
        }
        PessoaModel pessoa = pessoaRepository.findById(idPessoa);

        if(pessoa == null){
            throw new NotFoundException("Pessoa não encontrada.");
        }

        endereco.setBairro(enderecoDTO.bairro());
        endereco.setCep(enderecoDTO.cep());
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setComplemento(enderecoDTO.complemento());
        endereco.setEstado(enderecoDTO.estado());
        endereco.setLogadouro(enderecoDTO.logradouro());

        enderecoRepository.persist(endereco);


        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    public void delete(String nome, Long idEndereco) {
        EnderecoModel endereco = enderecoRepository.findById(idEndereco);
        if (endereco == null) {
            throw new NotFoundException("Endereço não encontrado.");
        }

        PessoaModel pessoa = pessoaRepository.findById(endereco.getPessoa().getId());
        if (pessoa == null) {
            pessoa.getEnderecos().remove(endereco);
        }
        enderecoRepository.delete(endereco);
    }

    @Override
    public EnderecoResponseDTO findById(Long idEndereco) {
        EnderecoModel endereco = enderecoRepository.findById(idEndereco);
        if (endereco == null) {
            throw new NotFoundException("Endereço não encontrado");
        }
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    public List<EnderecoResponseDTO> findByCep(String cep) {
        return enderecoRepository.findByCep(cep).stream()
                .map(e -> EnderecoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<EnderecoResponseDTO> findByAll() {
        return enderecoRepository.listAll().stream()
       .map(e -> EnderecoResponseDTO.valueOf(e)).toList();
    }
    
}
