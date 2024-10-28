package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;

import java.util.List;

public interface PessoaFisicaService {
    public PessoaFisicaResponseDTO insert(PessoaFisicaDTO dto) throws Exception;

    public PessoaFisicaResponseDTO update(PessoaFisicaDTO dto, Long id);

    public void delete(Long id);

    public PessoaFisicaResponseDTO findById(Long id);

    public List<PessoaFisicaResponseDTO> findByAll();
}
