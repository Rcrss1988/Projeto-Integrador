package pi4.example.pi4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pi4.example.pi4.model.Cliente;
import pi4.example.pi4.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    // Mapeamento para exibir a lista de clientes
    @GetMapping
    public String listarClientes(Model model) {
        Iterable<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "listaClientes";
    }

    // Mapeamento para exibir o formulário de adição de clientes
    @GetMapping("/adicionar")
    public String exibirFormularioAdicao(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formularioCliente";
    }

    // Mapeamento para lidar com o envio do formulário de adição
    @PostMapping("/adicionar")
    public String adicionarCliente(@ModelAttribute Cliente cliente, Model model) {
        try {
            // Check for duplicate CPF before saving
            if (isCpfDuplicate(cliente.getCpf())) {
                model.addAttribute("error", "CPF já cadastrado. Por favor, insira um CPF único.");
                return "formularioCliente";
            }

            clienteRepository.save(cliente);
            logger.info("Cliente adicionado com sucesso: {}", cliente);
            return "redirect:/clientes";
        } catch (Exception e) {
            logger.error("Erro ao adicionar cliente: {}", e.getMessage(), e);
            model.addAttribute("error", "Erro ao adicionar cliente. Por favor, tente novamente.");
            return "formularioCliente";
        }
    }

    // Método para verificar duplicidade de CPF
    private boolean isCpfDuplicate(String cpf) {
        try {
            // Check if a client with the given CPF already exists
            boolean duplicate = clienteRepository.existsByCpf(cpf);
            if (duplicate) {
                logger.warn("Tentativa de adição de cliente com CPF duplicado: {}", cpf);
            }
            return duplicate;
        } catch (Exception e) {
            logger.error("Erro ao verificar duplicidade de CPF: {}", e.getMessage(), e);
            throw e; // Re-throw the exception for proper error handling
        }
    }

    // Mapeamento para exibir o formulário de edição de clientes
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de cliente inválido"));
        model.addAttribute("cliente", cliente);
        return "formularioClienteEditar";
    }

    // Mapeamento para lidar com o envio do formulário de edição
    @PostMapping("/editar/{id}")
public String editarCliente(@PathVariable Long id, @ModelAttribute Cliente cliente, Model model) {
    try {
        cliente.setId(id);
        clienteRepository.save(cliente);
        logger.info("Cliente atualizado com sucesso: {}", cliente);

        return "redirect:/clientes";
    } catch (Exception e) {
        logger.error("Erro ao atualizar cliente: {}", e.getMessage(), e);
        model.addAttribute("error", "Erro ao atualizar cliente. Por favor, tente novamente.");
        return "formularioClienteEditar";
    }
}


    // Mapeamento para excluir um cliente
    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes";
    }
}
