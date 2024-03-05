package pi4.example.pi4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pi4.example.pi4.model.Cliente;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Encontrar um cliente pelo CPF
    Optional<Cliente> findByCpf(String cpf);

    // Encontrar clientes pelo nome (ignorando case)
    List<Cliente> findByNomeIgnoreCase(String nome);

    // Encontrar clientes pelo endereço (ignorando case)
    List<Cliente> findByEnderecoIgnoreCase(String endereco);

    // Encontrar clientes pelo telefone
    Optional<Cliente> findByTelefone(String telefone);

    // Encontrar todos os clientes ordenados pelo nome
    List<Cliente> findAllByOrderByNomeAsc();

    // Contar o número total de clientes
    long count();

    // Verificar se existe um cliente com o CPF fornecido
    boolean existsByCpf(String cpf);

    // Excluir um cliente pelo CPF
    void deleteByCpf(String cpf);

    // Encontrar clientes que contenham o nome ou o CPF fornecido
    List<Cliente> findByNomeContainingOrCpfContaining(String nome, String cpf);

    // Consulta personalizada usando JPQL
    @Query("SELECT c FROM Cliente c WHERE c.endereco LIKE %:endereco%")
    List<Cliente> encontrarPorEnderecoContendo(@Param("endereco") String endereco);
}
