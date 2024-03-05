
    function confirmDelete(clienteId) {
        var confirmDelete = confirm('Deseja realmente excluir este cliente?');
        if (confirmDelete) {
            // Redirect to the delete endpoint if confirmed
            window.location.href = '/clientes/excluir/' + clienteId;
        }
    }