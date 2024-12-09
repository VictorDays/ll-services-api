package br.com.llservicos.services.pedido;

import java.util.List;
import java.util.Optional;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.dtos.PedidoDTO;
import br.com.llservicos.domain.pedido.dtos.PedidoResponseDTO;
import br.com.llservicos.domain.servico.ServicoModel;

public interface PedidoService {

public PedidoResponseDTO createPedido(PedidoDTO pedidoDTO);

public PedidoResponseDTO updatePedido(Long id, PedidoDTO pedidoDTO);

public void delete(Long id);

public PedidoResponseDTO getPedidoById(Long id);

public List<PedidoResponseDTO> getAllPedidos();

public List<PedidoResponseDTO> getPedidosByPessoaId(Long pessoaId);

public List<PedidoResponseDTO> getPedidosByStatus(String status);

public List<PedidoResponseDTO> getPedidosByDataServico(String dataServico);

public List<PedidoResponseDTO> getPedidosByValorTotal(Double valorTotal);

Optional<ServicoModel> findById(Long id);

}
   



