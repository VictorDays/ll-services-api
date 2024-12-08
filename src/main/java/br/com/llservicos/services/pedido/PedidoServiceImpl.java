package br.com.llservicos.services.pedido;

import java.util.List;
import java.util.Optional;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.dtos.PedidoDTO;
import br.com.llservicos.domain.pedido.dtos.PedidoResponseDTO;
import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.servico.ServicoModel;
import br.com.llservicos.repositories.PedidoRepository;
import br.com.llservicos.repositories.PessoaFisicaRepository;
import br.com.llservicos.repositories.PessoaRepository;
import br.com.llservicos.repositories.ServicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ServicoRepository servicoRepository; // Repositório para buscar o serviço

    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    PessoaFisicaRepository pessoaFisicaRepository;

    @Override
    @Transactional
    public PedidoResponseDTO createPedido(PedidoDTO pedidoDTO) {
        ServicoModel servico = servicoRepository.findById(pedidoDTO.servicoId());
        PedidoModel pedido = new PedidoModel();

        if (pedidoDTO.pessoaFisica() == null) {
            PessoaModel pessoa = pessoaRepository.findById(pedidoDTO.pessoaFisica());

            pedido.setStatus(pedidoDTO.status());
            pedido.setValorTotal(pedidoDTO.valorTotal());
            pedido.setServico(servico);
            pedido.setPessoa(pessoa);

            pedidoRepository.persist(pedido);
        } else {
            PessoaModel pessoa = pessoaRepository.findById(pedidoDTO.pessoaJuridica());

            pedido.setStatus(pedidoDTO.status());
            pedido.setValorTotal(pedidoDTO.valorTotal());
            pedido.setServico(servico);
            pedido.setPessoa(pessoa);

            pedidoRepository.persist(pedido);
        }
        return PedidoResponseDTO.valueOf(pedido);
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
        if (pedido.getId() == 0) { // ID == 0 -> Novo objeto
            pedidoRepository.getEntityManager().persist(pedido);
            return pedido;
        } else { // ID != 0 -> Objeto existente
            return pedidoRepository.getEntityManager().merge(pedido);
        }
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
