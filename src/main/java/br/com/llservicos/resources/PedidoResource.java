package br.com.llservicos.resources;

import java.util.List;
import java.util.Optional;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.dtos.PedidoDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaDTO;
import br.com.llservicos.services.pedido.PedidoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("pedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {
    
    private final PedidoService pedidoService; // Use um serviço para gerenciar a lógica de negócios

    @Inject
    public PedidoResource(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @POST
    public Response createPedido(PedidoDTO pedido) throws Exception{
        try {
            return Response.status(Response.Status.CREATED).entity(pedidoService.createPedido(pedido)).build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao inserir Pedido: " + e.getMessage()).build();

        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            pedidoService.delete(id);
            return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    //@Permitall
    public Response findAll() {
        return Response.ok(pedidoService.getAllPedidos()).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updResponse(@PathParam("id") Long id, PessoaFisicaDTO dto){
        try {
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
