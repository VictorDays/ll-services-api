package br.com.llservicos.controllers;


import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;
import br.com.llservicos.services.pessoa.PessoaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.logging.Logger;

@Path("pessoa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource {

    private static final Logger LOG = Logger.getLogger(PessoaResource.class.getName());

    @Inject
    PessoaService pessoaService;

    @GET
    public List<PessoaResponseDTO> findAll() {
        LOG.info("Buscando Pessoas");
        return pessoaService.findAll();
    }
}
