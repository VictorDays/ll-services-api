package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaResponseDTO;

import java.util.List;

public interface PessoaJuridicaService {
    public PessoaJuridicaResponseDTO insert(PessoaJuridicaDTO dto) throws Exception;

    public PessoaJuridicaResponseDTO update(PessoaJuridicaDTO dto, Long id);

    public void delete(Long id);

    public PessoaJuridicaResponseDTO findById(Long id);

    public List<PessoaJuridicaResponseDTO> findByAll();
}
