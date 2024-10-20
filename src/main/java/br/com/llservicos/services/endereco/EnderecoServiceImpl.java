package br.com.llservicos.services.endereco;
import java.util.List;

import br.com.llservicos.domain.endereco.EnderecoModel;
import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.endereco.dtos.EnderecoResponseDTO;
import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.repositories.EnderecoRepository;
import br.com.llservicos.repositories.PessoaRepository;
import br.com.llservicos.services.pessoa.PessoaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {


    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    PessoaService pessoaService;



    @Override
    @Transactional
    public EnderecoResponseDTO insert(@Valid EnderecoDTO enderecoDTO, Long idPessoa) {
        PessoaModel pessoa = pessoaRepository.findById(idPessoa);

        EnderecoModel endereco = new EnderecoModel();
        endereco.setBairro(enderecoDTO.bairro());
        endereco.setCep(enderecoDTO.cep());
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setComplemento(enderecoDTO.complemento());
        endereco.setEstado(enderecoDTO.estado());
        endereco.setLogadouro(enderecoDTO.logradouro());


        pessoa.getEnderecos().add(endereco);

        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(String nome, Long idEndereco, Long idPessoa, @Valid EnderecoDTO enderecoDTO) {
        EnderecoModel endereco = enderecoRepository.findById(idEndereco);
        if(endereco == null){
            throw new NotFoundException("Endereço não encontrado.");
        }else{
            PessoaModel pessoa = pessoaRepository.findById(idPessoa);
            EnderecoModel enderecoAtualizado = new EnderecoModel();

            for(EnderecoModel end : pessoa.getEnderecos()){
                if(end.getId().equals(idEndereco)){
                    end.setBairro(enderecoDTO.bairro());
                    end.setCep(enderecoDTO.cep());
                    end.setCidade(enderecoDTO.cidade());
                    end.setComplemento(enderecoDTO.complemento());
                    end.setEstado(enderecoDTO.estado());
                    end.setLogadouro(enderecoDTO.logradouro());
                    enderecoAtualizado = end;
                    enderecoRepository.persist(end);
                }
            }
        }
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    public void delete(String nome, Long idEndereco) {
        PessoaModel pessoa = pessoaRepository.findById(idEndereco);
        EnderecoModel endereco = new EnderecoModel();

        for (EnderecoModel end : pessoa.getEnderecos()) {
            if (end.getId().equals(idEndereco)) {
                endereco = end;
            }
        }

        pessoa.getEnderecos().remove(endereco);
        if(!enderecoRepository.deleteById(idEndereco)){
            throw new NotFoundException("Endereço não encontrado.");
        }
    }

    @Override
    public EnderecoResponseDTO findById(Long idEndereco) {
        return EnderecoResponseDTO.valueOf(enderecoRepository.findById(idEndereco));
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
