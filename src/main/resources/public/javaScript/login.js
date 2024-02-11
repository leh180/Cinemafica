document.getElementById('form-add').addEventListener('submit', function(event) {
    event.preventDefault();

    var formData = new FormData(this);

    fetch(this.action, {
        method: 'POST',
        body: formData
    })
    .then(function(response) {
        return response.text();
    })
    .then(function(data) {
    console.log(data);
    if (data.includes('Senha incorreta.')) {
        alert('Senha incorreta.');
    } else {
        document.getElementById('error-message').innerText = data;
    }
})
    .catch(function(error) {
        console.error('Error:', error);
    });
});

