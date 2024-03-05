function validateForm() {
    var nome = document.getElementById('nome').value.trim();
    var cpf = document.getElementById('cpf').value.trim();
    var endereco = document.getElementById('endereco').value.trim();
    var telefone = document.getElementById('telefone').value.trim();

    // Log para verificar se os valores estão corretos
    console.log("Nome:", nome);
    console.log("CPF:", cpf);
    console.log("Endereço:", endereco);
    console.log("Telefone:", telefone);

    // Limpar mensagens de erro anteriores
    document.getElementById('errorMessages').innerText = "";

    // Validar se os campos estão preenchidos
    if (nome === "" || cpf === "" || endereco === "" || telefone === "") {
        displayError("Por favor, preencha todos os campos.");
        return false;
    }

    // Validar o formato do CPF
    if (!validateCPF(cpf)) {
        displayError("Por favor, insira um CPF válido.");
        return false;
    }

    // Validar o formato do telefone
    if (!validateTelefone(telefone)) {
        displayError("Por favor, insira um número de telefone válido.");
        return false;
    }

    // Validar comprimento do endereço
    if (endereco.length < 5) {
        displayError("O endereço deve ter pelo menos 5 caracteres.");
        return false;
    }

    // Verificar duplicidade no banco de dados
    checkDuplicidade(nome, cpf);

    // Impedir o envio do formulário por enquanto
    return false;
}

function displayError(message) {
    document.getElementById('errorMessages').innerText = message;
}

function validateCPF(cpf) {
    var cpfRegex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/;
    return cpfRegex.test(cpf);
}

function validateTelefone(telefone) {
    var telefoneRegex = /^\(\d{2}\) \d{5}-\d{4}$/;
    return telefoneRegex.test(telefone);
}



