package pi4.example.pi4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pi4.example.pi4.model.Banho;
import pi4.example.pi4.model.Pet;
import pi4.example.pi4.repository.BanhoRepository;
import pi4.example.pi4.repository.PetRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BanhoController {

    private static final Logger logger = LoggerFactory.getLogger(BanhoController.class);

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private BanhoRepository banhoRepository;

    // Show the form for scheduling a bath for a pet
    @GetMapping("/agendar-banho/{petId}")
    public String mostrarFormularioAgendarBanho(@PathVariable Long petId, Model model) {
        logger.info("Recebida requisição GET para /agendar-banho/{}", petId);

        // Retrieve the pet based on the ID
        Pet pet = petRepository.findById(petId).orElse(null);

        if (pet != null) {
            logger.info("Pet encontrado: {}", pet);

            // Create a new bath and set default values
            Banho banho = new Banho();
            banho.setPet(pet);
            banho.setDataAgendamento(LocalDateTime.now()); // Default to the current date and time

            model.addAttribute("pet", pet);
            model.addAttribute("banho", banho);

            logger.info("Exibindo o formulário para agendar banho");

            return "formularioBanho";
        } else {
            logger.warn("Pet não encontrado com o ID: {}", petId);
            return "redirect:/clientes";
        }
    }

    // Schedule a bath for a pet
    @PostMapping("/agendar-banho/{petId}")
    public String agendarBanho(@PathVariable Long petId, Banho banho) {
        logger.info("Recebida requisição POST para /agendar-banho/{}", petId);

        // Retrieve the pet based on the ID
        Pet pet = petRepository.findById(petId).orElse(null);

        if (pet != null) {
            logger.info("Pet encontrado: {}", pet);

            // Set pet and initial status for the bath
            banho.setPet(pet);
            banho.setStatus("Agendado"); // Set the initial status

            // Save the bath to the repository
            banhoRepository.save(banho);

            logger.info("Banho agendado com sucesso");

            // Redirect to the list of pets for the corresponding client
            return "redirect:/pets/listarPets/" + pet.getCliente().getId();
        } else {
            logger.warn("Pet não encontrado com o ID: {}", petId);
            return "redirect:/clientes";
        }
    }

    // List all scheduled baths
    @GetMapping("/banhos")
    public String listarBanhos(Model model) {
        List<Banho> banhos = banhoRepository.findAll();
        model.addAttribute("banhos", banhos);

        logger.info("Exibindo a lista de banhos");

        return "listaBanhos";
    }

    // Show the form for editing a scheduled bath
    @GetMapping("/editar-banho/{id}")
    public String mostrarFormularioEditarBanho(@PathVariable Long id, Model model) {
        Banho banho = banhoRepository.findById(id).orElse(null);

        if (banho != null) {
            model.addAttribute("banho", banho);

            logger.info("Exibindo o formulário para editar banho");

            return "formularioBanhoEditar";
        } else {
            // Handle case where Banho is not found
            logger.warn("Banho não encontrado com o ID: {}", id);
            return "redirect:/banhos";
        }
    }

    // Edit a scheduled bath
    @PostMapping("/editar-banho/{id}")
    public String editarBanho(@PathVariable Long id, @ModelAttribute Banho banho) {
        banho.setId(id);
        banhoRepository.save(banho);

        logger.info("Banho editado com sucesso");

        return "redirect:/banhos";
    }

    // Delete a scheduled bath
    @GetMapping("/excluir-banho/{id}")
    public String excluirBanho(@PathVariable Long id) {
        banhoRepository.deleteById(id);

        logger.info("Banho excluído com sucesso");

        return "redirect:/banhos";
    }
    


}
