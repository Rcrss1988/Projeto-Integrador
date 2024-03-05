/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pi4.example.pi4.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi4.example.pi4.model.Cliente;
import pi4.example.pi4.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    public List<Pet> findByCliente(Cliente cliente);

    
}
