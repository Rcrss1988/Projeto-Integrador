package pi4.example.pi4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pi4.example.pi4.model.Banho;

public interface BanhoRepository extends JpaRepository<Banho, Long> {
    // Você pode adicionar métodos específicos, se necessário
}
