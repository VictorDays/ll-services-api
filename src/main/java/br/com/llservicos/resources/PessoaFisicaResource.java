package br.com.llservicos.resources;


import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaDTO;
import br.com.llservicos.services.pessoa.PessoaFisicaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

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
    @Path("create")
    //@RolesAllowed({ "Admin" })
    public Response insert(PessoaFisicaDTO dto) throws Exception {
        try {
            LOG.info("Inserindo Pessoa Fisica");
            return Response.status(Response.Status.CREATED).entity(service.insert(dto)).build();
        } catch (ConstraintViolationException e) {
            LOG.info("Deu errado incluir Pessoa Fisica ");
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao inserir Pessoa Física: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Transactional
    @Path("delete/{id}")
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
    @Path("getall")
    //@Permitall
    public Response findAll() {
        LOG.info("Buscando Pessoa Fisica.");
        return Response.ok(service.findByAll()).build();
    }

    @PUT
    @Transactional
    @Path("update/{id}")
    public Response updResponse(@PathParam("id") Long id, PessoaFisicaDTO dto){
        try {
            service.update(dto, id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
