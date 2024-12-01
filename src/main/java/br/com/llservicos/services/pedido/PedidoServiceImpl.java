package br.com.llservicos.services.pedido;

import java.util.List;
import java.util.Optional;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.dtos.PedidoDTO;

import br.com.llservicos.repositories.PedidoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class PedidoServiceImpl implements PedidoService{
   
    @Inject
    PedidoRepository pedidoRepository;

    

    @Override
    @Transactional
    public PedidoModel createPedido(PedidoDTO pedidoDTO) {
        PedidoModel pedido = new PedidoModel(0, null, null, null);
        pedido.setStatus(pedidoDTO.status());
        pedido.setValorTotal(pedidoDTO.valorTotal());

        pedidoRepository.persist(pedido);
        return pedido;
    }

    @Override
    @Transactional
    public PedidoModel updatePedido(Long id, PedidoDTO pedidoDTO) {
        PedidoModel pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new RuntimeException("Pedido não encontrado");
        }
        
        pedido.setStatus(pedidoDTO.status());
        pedido.setValorTotal(pedidoDTO.valorTotal());

        return pedido;
    }

    @Override
    @Transactional
    public boolean deletePedido(Long id) {
        return pedidoRepository.deleteById(id);
    }

    @Override
    public PedidoModel getPedidoById(Long id) {
        PedidoModel pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new RuntimeException("Pedido não encontrado");
        }
        return pedido;
    }

    @Override
    public List<PedidoModel> getAllPedidos() {
        return pedidoRepository.findAll().list();
    }

    @Override
    public List<PedidoModel> getPedidosByPessoaId(Long pessoaId) {
        return pedidoRepository.find("pessoaId", pessoaId).list();
    }

    @Override
    public List<PedidoModel> getPedidosByStatus(String status) {
        return pedidoRepository.find("status", status).list();
    }

    @Override
    public List<PedidoModel> getPedidosByDataServico(String dataServico) {
        return pedidoRepository.find("dataServico", dataServico).list();
    }

    @Override
    public List<PedidoModel> getPedidosByValorTotal(Double valorTotal) {
        return pedidoRepository.find("valorTotal", valorTotal).list();
    }
    

     @Override
    @Transactional
    public PedidoModel save(PedidoModel pedido) {
        pedidoRepository.persist(pedido);
        return pedido;
    }

    @Override
    public Optional<PedidoModel> findById(Long id) {
        return pedidoRepository.findByIdOptional(id);
    }

    @Override
    public List<PedidoModel> findAll() {
        return pedidoRepository.listAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
    pedidoRepository.deleteById(id); 
    }
}
