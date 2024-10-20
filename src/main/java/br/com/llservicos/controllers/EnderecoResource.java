package br.com.llservicos.controllers;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.endereco.dtos.EnderecoResponseDTO;
import br.com.llservicos.services.endereco.EnderecoService;
import io.quarkus.logging.Log;
import io.quarkus.vertx.http.runtime.devmode.Json;
import io.vertx.core.logging.Logger;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    @POST
    @Path("/{idPessoa}")
    public Response create(@PathParam("idPessoa") Long idPessoa, @Valid EnderecoDTO enderecoDTO) {
        EnderecoResponseDTO endereco = enderecoService.insert(enderecoDTO, idPessoa);
        return Response.status(Response.Status.CREATED).entity(endereco).build();
    }

    @PUT
    @Path("/{idEndereco}/{idPessoa}")
    public Response update(
        @PathParam("idEndereco") Long idEndereco,
        @PathParam("idPessoa") Long idPessoa,
        @Valid EnderecoDTO enderecoDTO
    ) {
        EnderecoResponseDTO endereco = enderecoService.update(null, idEndereco, idPessoa, enderecoDTO);
        return Response.ok(endereco).build();
    }

    @DELETE
    @Path("/{idEndereco}")
    public Response delete(@PathParam("idEndereco") Long idEndereco) {
        enderecoService.delete(null, idEndereco);
        return Response.noContent().build();
    }

    @GET
    @Path("/{idEndereco}")
    public Response findById(@PathParam("idEndereco") Long idEndereco) {
        EnderecoResponseDTO endereco = enderecoService.findById(idEndereco);
        return Response.ok(endereco).build();
    }

    @GET
    @Path("/cep/{cep}")
    public Response findByCep(@PathParam("cep") String cep) {
        Log.infof("Buscando endereço pelo CEP: %s", cep);
        return Response.ok(enderecoService.findByCep(cep)).build();
    }

    @GET
    public Response findByAll() {
        Log.info("Buscando todos os endereços");
        return Response.ok(enderecoService.findByAll()).build();
    }

    
   
}
