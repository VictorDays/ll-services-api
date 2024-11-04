package br.com.llservicos.controllers;

import br.com.llservicos.domain.servico.ServicoModel;
import br.com.llservicos.services.servico.ServicoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("servico")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServicoResource {

    private final ServicoService servicoService;

    @Inject
    public ServicoResource(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @POST
    public Response createServico(ServicoModel servico) {
        ServicoModel createdServico = servicoService.save(servico);
        return Response.status(Response.Status.CREATED).entity(createdServico).build();
    }

    @GET
    @Path("{id}")
    public Response getServicoById(@PathParam("id") Long id) {
        Optional<ServicoModel> servico = servicoService.findById(id);
        return servico.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response getAllServicos() {
        List<ServicoModel> servicos = servicoService.findAll();
        return Response.ok(servicos).build();
    }

    @PUT
    @Path("{id}")
    public Response updateServico(@PathParam("id") Long id, ServicoModel servico) {
        Optional<ServicoModel> existingServico = servicoService.findById(id);
        if (existingServico.isPresent()) {
            servico.setId(id);
            ServicoModel updatedServico = servicoService.save(servico);
            return Response.ok(updatedServico).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteServico(@PathParam("id") Long id) {
        Optional<ServicoModel> servico = servicoService.findById(id);
        if (servico.isPresent()) {
            servicoService.deleteById(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}