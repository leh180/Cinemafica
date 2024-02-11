document.getElementById('form-add').addEventListener('submit', function(event) {
    event.preventDefault();

    var formData = new FormData(this);

    fetch(this.action, {
        method: 'POST',
        headers: {
      'Content-Type': 'application/json'  // Define o tipo de conteúdo como JSON
      },
        body: formData
    })
    .then(function(response) {
        if (response.ok) {
            window.location.href = 'login.html';
        }
    })
    .then(function(data) {
        window.location.href = 'login.html';
    })
    .catch(function(error) {
        console.error('Error:', error);
    });
});
