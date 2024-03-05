package pi4.example.pi4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;

@Entity
public class Banho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataAgendamento;
    private String observacoes;
    private String status; // Adicionado o status do serviço
    private Double valor; // Adicionado o valor do serviço

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    // Construtor vazio necessário para JPA
    public Banho() {
    }

    public Banho(Long id, LocalDateTime dataAgendamento, String observacoes, String status, Double valor, Pet pet) {
        this.id = id;
        this.dataAgendamento = dataAgendamento;
        this.observacoes = observacoes;
        this.status = status;
        this.valor = valor;
        this.pet = pet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
