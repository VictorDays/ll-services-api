package br.com.llservicos.services.pessoa;

import br.com.llservicos.domain.pessoa.dtos.PessoaDTO;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.PessoaJuridicaModel;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaResponseDTO;
import br.com.llservicos.repositories.PessoaFisicaRepository;
import br.com.llservicos.repositories.PessoaJuridicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PessoaServiceImpl implements PessoaService{
    @Inject
    PessoaFisicaRepository fisicaRepository;
    @Inject
    PessoaJuridicaRepository juridicaRepository;

    @Override
    public List<PessoaResponseDTO> findAll() {

        List<PessoaResponseDTO> listaPessoas = new ArrayList<>();

        List<PessoaResponseDTO> listaPFisicas = fisicaRepository.findAll().stream()
                .map(f -> PessoaResponseDTO.valueOf(f)).toList();

        List<PessoaResponseDTO> listaPJuridica = juridicaRepository.findAll().stream()
                .map(j -> PessoaResponseDTO.valueOf(j)).toList();

        // Adicionando as pessoas físicas e jurídicas à lista combinada
        listaPessoas.addAll(listaPFisicas);
        listaPessoas.addAll(listaPJuridica);

        return listaPessoas;
    }

    @Override
    public PessoaResponseDTO findId() {
        return null;
    }

    public Response create(PessoaDTO pessoaDTO){
        if (pessoaDTO.cpf() != null) {
            PessoaFisicaModel pessoaFisica = new PessoaFisicaModel();
            pessoaFisica.setNome(pessoaDTO.nome());
            pessoaFisica.setCpf(pessoaDTO.cpf());
            pessoaFisica.setEmail(pessoaDTO.email());
            pessoaFisica.setTelefone(pessoaDTO.usuario().telefone());

            fisicaRepository.persist(pessoaFisica);

            return Response.ok(PessoaFisicaResponseDTO.valueOf(pessoaFisica)).build();

        } else if (pessoaDTO.cnpj() != null) {
            PessoaJuridicaModel pessoaJuridica = new PessoaJuridicaModel();
            pessoaJuridica.setNome(pessoaDTO.nome());
            pessoaJuridica.setCnpj(pessoaDTO.cnpj());
            pessoaJuridica.setEmail(pessoaDTO.email());
            pessoaJuridica.setTelefone(pessoaDTO.usuario().telefone());

            juridicaRepository.persist(pessoaJuridica);

            return Response.ok(PessoaJuridicaResponseDTO.valueOf(pessoaJuridica)).build();

        } else {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("CPF ou CNPJ devem ser informados!")
                    .build();
        }
    }
}
