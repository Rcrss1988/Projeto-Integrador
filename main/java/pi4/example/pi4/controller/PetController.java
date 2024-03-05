/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pi4.example.pi4.controller;

/**
 *
 * @author Raphael
 */
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pi4.example.pi4.model.Cliente;
import pi4.example.pi4.model.Pet;
import pi4.example.pi4.repository.ClienteRepository;
import pi4.example.pi4.repository.PetRepository;

@Controller
public class PetController {

    private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    // Supondo que você tenha um repositório para clientes
    @Autowired
    private ClienteRepository clienteRepository;

    // Supondo que você tenha um repositório para pets
    @Autowired
    private PetRepository petRepository;

    @GetMapping("/pets/adicionarPET/{clienteId}")
    public String mostrarFormularioAdicionarPet(@PathVariable Long clienteId, Model model) {
        logger.info("Recebida requisição GET para /pets/adicionarPET/{}", clienteId);

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente != null) {
            logger.info("Cliente encontrado: {}", cliente);
            model.addAttribute("cliente", cliente);
            model.addAttribute("pet", new Pet());
            return "formularioPet";
        } else {
            logger.warn("Cliente não encontrado com o ID: {}", clienteId);
            return "redirect:/clientes";
        }
    }

    @PostMapping("/pets/adicionarPET/{clienteId}")
    public String adicionarPet(@PathVariable Long clienteId, Pet pet) {
        logger.info("Recebida requisição POST para /pets/adicionarPET/{}", clienteId);

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente != null) {
            logger.info("Cliente encontrado: {}", cliente);
            pet.setCliente(cliente);
            petRepository.save(pet);
            logger.info("Pet adicionado com sucesso");
            return "redirect:/clientes";
        } else {
            logger.warn("Cliente não encontrado com o ID: {}", clienteId);
            return "redirect:/clientes";
        }
    }

    // GetMapping for displaying Pet details
    @GetMapping("/pets/detalhes/{petId}")
    public String mostrarDetalhesPet(@PathVariable Long petId, Model model) {
        logger.info("Recebida requisição GET para /pets/detalhes/{}", petId);

        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet != null) {
            logger.info("Pet encontrado: {}", pet);
            model.addAttribute("pet", pet);
            return "detalhesPet";
        } else {
            logger.warn("Pet não encontrado com o ID: {}", petId);
            return "redirect:/clientes";
        }
    }

    // GetMapping for displaying Pet update form
    @GetMapping("/pets/editarPET/{petId}")
    public String mostrarFormularioEditarPet(@PathVariable Long petId, Model model) {
        logger.info("Recebida requisição GET para /pets/editarPET/{}", petId);

        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet != null) {
            logger.info("Pet encontrado: {}", pet);
            model.addAttribute("pet", pet);
            return "formularioPetEditar";
        } else {
            logger.warn("Pet não encontrado com o ID: {}", petId);
            return "redirect:/clientes";
        }
    }

    // PostMapping for updating Pet details
    @PostMapping("/pets/editarPET/{petId}")
    public String editarPet(@PathVariable("petId") Long id, @ModelAttribute Pet pet) {
        logger.info("Recebida requisição POST para /pets/editarPET/{}", id);

        try {
            Pet existingPet = petRepository.findById(id).orElse(null);

            if (existingPet != null) {
                logger.info("Pet encontrado: {}", existingPet);

                // Atualizar os detalhes do pet
                existingPet.setNome(pet.getNome());
                existingPet.setEspecie(pet.getEspecie());
                existingPet.setCor(pet.getCor());
                existingPet.setPorte(pet.getPorte());

                // Salvar as alterações no repositório
                petRepository.save(existingPet);

                logger.info("Pet atualizado com sucesso");

                // Redirecionar para a lista de pets do cliente
                return "redirect:/pets/listarPets/" + existingPet.getCliente().getId();
            } else {
                logger.warn("Pet não encontrado com o ID: {}", id);
            }
        } catch (Exception e) {
            logger.error("Erro ao editar o pet com ID: {}", id, e);
        }

        // Redirecionar em caso de erro ou pet não encontrado
        return "redirect:/clientes";
    }

    // GetMapping for deleting Pet
    @GetMapping("/pets/excluirPET/{petId}")
    public String excluirPet(@PathVariable Long petId) {
        logger.info("Recebida requisição GET para /pets/excluirPET/{}", petId);

        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet != null) {
            logger.info("Pet encontrado: {}", pet);
            petRepository.delete(pet);
            logger.info("Pet excluído com sucesso");
        } else {
            logger.warn("Pet não encontrado com o ID: {}", petId);
        }
        // Redirect to the list of pets for the corresponding client
        return "redirect:/pets/listarPets/" + pet.getCliente().getId();
    }

    // GetMapping to list all Pets for a given ClienteId
    @GetMapping("/pets/listarPets/{clienteId}")
    public String listarPetsPorCliente(@PathVariable Long clienteId, Model model) {
        logger.info("Recebida requisição GET para /pets/listarPets/{}", clienteId);

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente != null) {
            logger.info("Cliente encontrado: {}", cliente);

            List<Pet> pets = petRepository.findByCliente(cliente);
            model.addAttribute("cliente", cliente);
            model.addAttribute("pets", pets);

            return "listaDogsPorCliente";
        } else {
            logger.warn("Cliente não encontrado com o ID: {}", clienteId);
            return "redirect:/clientes";
        }
    }
}
