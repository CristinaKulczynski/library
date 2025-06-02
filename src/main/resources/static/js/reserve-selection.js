

let selectedCode = null;
let selectedName = null;

window.addEventListener('DOMContentLoaded', () => {

    const rows = document.querySelectorAll('#availTable tbody tr');
    const inputSelected = document.getElementById('selectedBook');
    const btnReserve = document.getElementById('reserveBtn');

    rows.forEach(row => {

        if (!row.hasAttribute('data-code')) return;

        row.addEventListener('click', () => {

            rows.forEach(r => r.classList.remove('selected'));
            row.classList.add('selected');


            selectedCode = row.getAttribute('data-code');
            selectedName = row.getAttribute('data-name');
            inputSelected.value = selectedName;
            btnReserve.disabled = false;
        });
    });


    btnReserve.addEventListener('click', (e) => {
        e.preventDefault();

        if (!selectedCode) {
            alert('Selecione primeiro um livro disponível para reservar.');
            return;
        }

        fetch(`/reservar/${selectedCode}`, {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': getCsrfToken()
            }
        })
        .then(response => {
            if (response.ok) {

                window.location.reload();
            } else {
                alert('Não foi possível reservar este livro.');
                window.location.reload();
            }
        })
        .catch(() => {
            alert('Erro ao tentar reservar. Tente novamente mais tarde.');
        });
    });
});


function getCsrfToken() {
    const meta = document.querySelector('meta[name="_csrf"]');
    return meta ? meta.getAttribute('content') : '';
}
