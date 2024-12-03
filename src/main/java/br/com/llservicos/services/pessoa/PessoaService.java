package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.pessoa.dtos.PessoaDTO;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import br.com.llservicos.domain.pessoa.pessoajuridica.PessoaJuridicaModel;
import br.com.llservicos.repositories.PessoaFisicaRepository;
import br.com.llservicos.repositories.PessoaJuridicaRepository;
import br.com.llservicos.repositories.PessoaRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class PessoaService {
    @Inject
    PessoaJuridicaRepository juridicaRepository;

    @Inject
    PessoaRepository pessoaRepository;

    private static final Logger LOG = Logger.getLogger(String.valueOf(PessoaService.class));
    // Método para buscar tanto Pessoa Física quanto Pessoa Jurídica pelo telefone
    public PessoaResponseDTO buscarPessoaPorTelefone(String telefone) {
        PessoaModel pessoa = pessoaRepository.findBytelefone(telefone);
        if (pessoa != null) {
            return PessoaResponseDTO.valueOf(pessoa);
        } else {
            return null;
        }
    }

}
