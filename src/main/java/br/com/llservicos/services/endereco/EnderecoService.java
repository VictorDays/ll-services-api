package br.com.llservicos.services.endereco;

import java.util.List;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.endereco.dtos.EnderecoResponseDTO;
import jakarta.validation.Valid;

public interface EnderecoService {
    public EnderecoResponseDTO insert(@Valid EnderecoDTO enderecoDTO, Long idCliente);

    public EnderecoResponseDTO update(String nome, Long idEndereco, Long idUsuario, @Valid EnderecoDTO enderecoDTO);

    public void delete(String nome, Long idEndereco);

    public EnderecoResponseDTO findById(Long idEndereco);

    public List<EnderecoResponseDTO> findByCep(String cep);

    public List<EnderecoResponseDTO> findAll();

    
} 