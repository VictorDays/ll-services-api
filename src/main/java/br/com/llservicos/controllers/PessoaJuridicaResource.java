package br.com.llservicos.controllers;


import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaResponseDTO;
import br.com.llservicos.services.pessoa.PessoaJuridicaService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.logging.Logger;

@Path("/pessoajuridica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaJuridicaResource {
    @Inject
    PessoaJuridicaService service;

    private static final Logger LOG = Logger.getLogger(PessoaJuridicaResource.class.getName());

    @POST
    @Transactional
    //@RolesAllowed({ "Admin" })
    public Response insert(PessoaJuridicaDTO dto) throws Exception {
        try {
            LOG.info("Inserindo Pessoa Juridica");
            return Response.status(Response.Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            LOG.info("Deu errado incluir Pessoa Juridica ");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            LOG.info("Deletando o Pessoa Juridica");
            service.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            LOG.info("Errod ao deletar Pessoa Juridica.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    //@Permitall
    public Response findAll() {
        LOG.info("Buscando Pessoa Juridica.");
        return Response.ok(service.findByAll()).build();
    }

}
