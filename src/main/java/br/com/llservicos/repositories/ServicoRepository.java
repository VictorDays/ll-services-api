package br.com.llservicos.repositories;

import java.util.Collections;
import java.util.List;

import br.com.llservicos.domain.servico.ServicoModel;
import br.com.llservicos.domain.servico.dto.ServicoResponseDTO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServicoRepository implements PanacheRepository<ServicoModel> {
    // Busca todos os nomes ordenados pelo ID em ordem crescente
    public List<String> findAllNomesOrderedById() {
        return getEntityManager()
                .createQuery("SELECT s.nome FROM ServicoModel s ORDER BY s.id ASC", String.class)
                .getResultList();
    }

    // Busca todas as descrições ordenadas pelo ID em ordem crescente
    public List<String> findAllDescricoesOrderedById() {
        return getEntityManager()
                .createQuery("SELECT s.descricao FROM ServicoModel s ORDER BY s.id ASC", String.class)
                .getResultList();
    }

    public ServicoResponseDTO buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo.");
        }

        // Busca o ServicoModel pelo ID
        ServicoModel servicoModel = findById(id);


        // Converte o ServicoModel para ServicoResponseDTO
        return new ServicoResponseDTO(
                servicoModel.getId(),
                servicoModel.getNome(),
                servicoModel.getDescricao()
        );
    }
    public String findNomeByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return null; // Retorna null se o nome for nulo ou vazio
        }

        // Realiza a busca no banco, sem considerar maiúsculas/minúsculas, e retorna apenas o nome do primeiro serviço encontrado
        return getEntityManager()
                .createQuery("SELECT s.nome FROM ServicoModel s WHERE UPPER(s.nome) LIKE UPPER(?1)", String.class)
                .setParameter(1, "%" + nome + "%")
                .setMaxResults(1) // Garante que apenas um resultado seja retornado
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null); // Retorna null se nenhum resultado for encontrado
    }
}
