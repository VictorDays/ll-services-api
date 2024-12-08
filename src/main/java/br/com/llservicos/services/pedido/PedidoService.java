package br.com.llservicos.services.pedido;

import java.util.List;
import java.util.Optional;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.dtos.PedidoDTO;
import br.com.llservicos.domain.pedido.dtos.PedidoResponseDTO;

public interface PedidoService {

public PedidoResponseDTO createPedido(PedidoDTO pedidoDTO);

public PedidoModel updatePedido(Long id, PedidoDTO pedidoDTO);

public boolean deletePedido(Long id);

public PedidoModel getPedidoById(Long id);

public List<PedidoModel> getAllPedidos();

public List<PedidoModel> getPedidosByPessoaId(Long pessoaId);

public List<PedidoModel> getPedidosByStatus(String status);

public List<PedidoModel> getPedidosByDataServico(String dataServico);

public List<PedidoModel> getPedidosByValorTotal(Double valorTotal);

PedidoModel save(PedidoModel pedido);

List<PedidoModel> findAll();

void deleteById(Long id);

Optional<PedidoModel> findById(Long id);

}
   



