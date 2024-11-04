package br.com.llservicos.resources;

import java.util.List;
import java.util.Optional;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.services.pedido.PedidoService;
import jakarta.inject.Inject;
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
    public Response createPedido(PedidoModel pedido) {
        PedidoModel createdPedido = pedidoService.save(pedido); // Use o serviço para salvar o pedido
        return Response.status(Response.Status.CREATED).entity(createdPedido).build();
    }

    @GET
    @Path("{id}")
    public Response getPedidoById(@PathParam("id") Long id) {
        Optional<PedidoModel> pedido = pedidoService.findById(id); // Use o serviço para encontrar o pedido
        return pedido.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response getAllPedidos() {
        List<PedidoModel> pedidos = pedidoService.findAll(); // Use o serviço para obter todos os pedidos
        return Response.ok(pedidos).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePedido(@PathParam("id") Long id, PedidoModel pedido) {
        Optional<PedidoModel> existingPedido = pedidoService.findById(id); // Use o serviço
        if (existingPedido.isPresent()) {
            pedido.setId(id);
            PedidoModel updatedPedido = pedidoService.save(pedido); // Use o serviço para atualizar o pedido
            return Response.ok(updatedPedido).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deletePedido(@PathParam("id") Long id) {
        if (pedidoService.deletePedido(id)) { // Verifique se a deleção foi bem-sucedida
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
