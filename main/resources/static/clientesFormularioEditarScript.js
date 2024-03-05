function validateForm() {
    // Get form inputs
    var nomeElement = document.getElementById('nome');
    var enderecoElement = document.getElementById('endereco');
    var telefoneElement = document.getElementById('telefone');

    // Check if elements exist in the document
    if (!nomeElement || !enderecoElement || !telefoneElement) {
        displayError('Erro: Elementos do formulário não encontrados.');
        return false;
    }

    // Extract values from elements
    var nome = nomeElement.value;
    var endereco = enderecoElement.value;
    var telefone = telefoneElement.value;

    // Basic validation example (you can customize this)
    if (nome.trim() === '') {
        displayError('Por favor, preencha o campo Nome.');
        return false;
    }

    if (endereco.trim().length < 5) {
        displayError('O endereço deve ter pelo menos 5 caracteres.');
        return false;
    }

    // Validate o formato do telefone
    if (!validateTelefone(telefone)) {
        displayError('Por favor, insira um número de telefone válido.');
        return false;
    }

    // Clear previous error messages if any
    displayError('');

    // If all validation passes, return true to submit the form
    return true;
}

// Function to validate the telefone format
function validateTelefone(telefone) {
    // Add your telefone format validation logic here
    // This is a placeholder function, customize it based on your requirements
    // For example, you can use a regular expression to validate the format
    var telefoneRegex = /^\(\d{2}\) \d{5}-\d{4}$/; // Change this regex based on your requirement

    return telefoneRegex.test(telefone);
}

// Function to display error messages
function displayError(message) {
    var errorMessagesElement = document.getElementById('errorMessages');
    if (errorMessagesElement) {
        errorMessagesElement.innerText = message;
    }
}
