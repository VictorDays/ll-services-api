package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.pessoa.dtos.PessoaDTO;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface PessoaService {
    public List<PessoaResponseDTO> findAll();
    public PessoaResponseDTO findId();
    public Response create(PessoaDTO pessoaDTO);
}
