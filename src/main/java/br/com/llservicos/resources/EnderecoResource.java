package br.com.llservicos.resources;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.endereco.dtos.EnderecoResponseDTO;
import br.com.llservicos.services.endereco.EnderecoService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    @POST
    @Path("create")
    public Response create(@Valid EnderecoDTO enderecoDTO) {
        EnderecoResponseDTO endereco = enderecoService.insert(enderecoDTO);
        return Response.status(Response.Status.CREATED).entity(endereco).build();
    }

    @PUT
    @Path("update/{idEndereco}/{idPessoa}")
    public Response update(
        @PathParam("idEndereco") Long idEndereco,
        @PathParam("idPessoa") Long idPessoa,
        @Valid EnderecoDTO enderecoDTO
    ) {
        EnderecoResponseDTO endereco = enderecoService.update(null, idEndereco, idPessoa, enderecoDTO);
        return Response.ok(endereco).build();
    }

    @DELETE
    @Path("delete/{idEndereco}")
    public Response delete(@PathParam("idEndereco") Long idEndereco) {
        enderecoService.delete(null, idEndereco);
        return Response.noContent().build();
    }

    @GET
    @Path("getid/{idEndereco}")
    public Response findById(@PathParam("idEndereco") Long idEndereco) {
        EnderecoResponseDTO endereco = enderecoService.findById(idEndereco);
        return Response.ok(endereco).build();
    }

    @GET
    @Path("getcep/{cep}")
    public Response findByCep(@PathParam("cep") String cep) {
        Log.infof("Buscando endereço pelo CEP: %s", cep);
        return Response.ok(enderecoService.findByCep(cep)).build();
    }

    @GET
    @Path("getall")
    public Response findByAll() {
        Log.info("Buscando todos os endereços");
        return Response.ok(enderecoService.findByAll()).build();
    }

    
   
}
