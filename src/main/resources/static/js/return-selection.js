// return-selection.js

let selectedCode = null;
let selectedName = null;

window.addEventListener('DOMContentLoaded', () => {
    const rows = document.querySelectorAll('#returnTable tbody tr');
    const inputSelected = document.getElementById('selectedReturnBook');
    const btnReturn = document.getElementById('returnBtn');

    rows.forEach(row => {
        if (!row.hasAttribute('data-code')) return;

        row.addEventListener('click', () => {
            rows.forEach(r => r.classList.remove('selected'));
            row.classList.add('selected');

            selectedCode = row.getAttribute('data-code');
            selectedName = row.getAttribute('data-name');

            inputSelected.value = selectedName;

            btnReturn.disabled = false;
        });
    });

    btnReturn.addEventListener('click', (e) => {
        e.preventDefault();
        if (!selectedCode) {
            alert('Selecione primeiro um livro para devolver.');
            return;
        }

        fetch(`/return_book/${selectedCode}`, {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': getCsrfToken()
            }
        })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Não foi possível devolver este livro.');
                window.location.reload();
            }
        })
        .catch(() => {
            alert('Erro na devolução. Tente novamente mais tarde.');
        });
    });
});

function getCsrfToken() {
    const meta = document.querySelector('meta[name="_csrf"]');
    return meta ? meta.getAttribute('content') : '';
}
