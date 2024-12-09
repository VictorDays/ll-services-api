package br.com.llservicos.services.pedido;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.Status;
import br.com.llservicos.domain.pedido.dtos.PedidoDTO;
import br.com.llservicos.domain.pedido.dtos.PedidoResponseDTO;
import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;
import br.com.llservicos.domain.servico.ServicoModel;
import br.com.llservicos.repositories.PedidoRepository;
import br.com.llservicos.repositories.PessoaFisicaRepository;
import br.com.llservicos.repositories.PessoaRepository;
import br.com.llservicos.repositories.ServicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ServicoRepository servicoRepository;

    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    PessoaFisicaRepository pessoaFisicaRepository;


    @Override
    @Transactional
    public PedidoResponseDTO createPedido(PedidoDTO dto) {
        ServicoModel servico =  servicoRepository.findById(dto.servicoId());
        PessoaModel pessoa = pessoaRepository.findById(dto.pessoa());

        PedidoModel pedido = new PedidoModel();
        pedido.setPessoa(pessoa);
        pedido.setStatus(Status.AGENDADO);
        pedido.setValorTotal(dto.valorTotal());
        pedido.setServico(servico);
        pedidoRepository.persist(pedido);

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public PedidoResponseDTO updatePedido(Long id, PedidoDTO dto) {
        ServicoModel servico =  servicoRepository.findById(dto.servicoId());
        PessoaModel pessoa = pessoaRepository.findById(dto.pessoa());
        PedidoModel pedido = pedidoRepository.findById(id);

        pedido.setPessoa(pessoa);
        pedido.setStatus(Status.AGENDADO);
        pedido.setValorTotal(dto.valorTotal());
        pedido.setServico(servico);
        pedidoRepository.persist(pedido);

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public void delete(Long id) {
        if (!pedidoRepository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public PedidoResponseDTO getPedidoById(Long id) {
        PedidoModel person = pedidoRepository.findById(id);
        if (person == null) {
            throw new EntityNotFoundException("Pedido não encontrado com ID: " + id);
        }
        return PedidoResponseDTO.valueOf(person);
    }

    @Override
    public List<PedidoResponseDTO> getAllPedidos() {
        return pedidoRepository.listAll().stream()
                .map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PedidoResponseDTO> getPedidosByPessoaId(Long pessoaId) {
        return null;
    }

    @Override
    public List<PedidoResponseDTO> getPedidosByStatus(String status) {
        return null;
    }

    @Override
    public List<PedidoResponseDTO> getPedidosByDataServico(String dataServico) {
        return null;
    }

    @Override
    public List<PedidoResponseDTO> getPedidosByValorTotal(Double valorTotal) {
        return null;
    }
}
