// src/main/resources/static/js/books-selection.js

let selectedCode = null;

window.addEventListener('DOMContentLoaded', () => {
    const rows = document.querySelectorAll('#booksTable tbody tr');

    rows.forEach(row => {
        if (!row.hasAttribute('data-code')) return;

        row.addEventListener('click', () => {
            rows.forEach(r => r.classList.remove('selected'));
            row.classList.add('selected');
            selectedCode = row.getAttribute('data-code');
            console.log('Livro selecionado:', selectedCode);
        });
    });
});
