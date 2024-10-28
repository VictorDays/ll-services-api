package br.com.llservicos.controllers;


import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaResponseDTO;
import br.com.llservicos.services.pessoa.PessoaFisicaService;
import io.quarkus.test.common.IntegrationTestStartedNotifier;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.jandex.Result;

import java.util.logging.Logger;

@Path("/pessoafisica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaFisicaResource {
    @Inject
    PessoaFisicaService service;

    private static final Logger LOG = Logger.getLogger(PessoaFisicaResource.class.getName());

    @POST
    @Transactional
    //@RolesAllowed({ "Admin" })
    public Response insert(PessoaFisicaDTO dto) throws Exception {
        try {
            LOG.info("Inserindo Pessoa Fisica");
            return Response.status(Response.Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            LOG.info("Deu errado incluir Pessoa Fisica ");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Pessoa Fisica");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            LOG.info("Erro ao deletar Pessoa Fisica.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    //@Permitall
    public Response findAll() {
        LOG.info("Buscando Pessoa Fisica.");
        return Response.ok(service.findByAll()).build();
    }


}
