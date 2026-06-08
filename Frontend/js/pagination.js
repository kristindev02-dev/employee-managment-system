// Simple pagination helper for tables
function setupPagination(tableBodyId, paginationId, data, rowRenderer, pageSize = 10) {
    const tableBody = document.getElementById(tableBodyId);
    const pagination = document.getElementById(paginationId);
    if (!tableBody) return;

    let currentPage = 1;
    const totalPages = Math.max(1, Math.ceil(data.length / pageSize));

    function renderPage(page) {
        currentPage = page;
        const start = (page - 1) * pageSize;
        const pageData = data.slice(start, start + pageSize);
        tableBody.innerHTML = pageData.map(rowRenderer).join('');
        renderControls();
    }

    function renderControls() {
        if (!pagination) return;
        if (totalPages <= 1) { pagination.innerHTML = ''; return; }

        let html = '<ul class="pagination">';
        html += `<li class="page-item ${currentPage === 1 ? 'disabled' : ''}"><a class="page-link" href="#" data-page="${currentPage - 1}">Prev</a></li>`;
        for (let i = 1; i <= totalPages; i++) {
            html += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" href="#" data-page="${i}">${i}</a></li>`;
        }
        html += `<li class="page-item ${currentPage === totalPages ? 'disabled' : ''}"><a class="page-link" href="#" data-page="${currentPage + 1}">Next</a></li>`;
        html += '</ul>';

        pagination.innerHTML = html;

        pagination.querySelectorAll('a.page-link').forEach(a => {
            a.addEventListener('click', function (e) {
                e.preventDefault();
                const p = parseInt(this.getAttribute('data-page'), 10);
                if (!isNaN(p) && p >= 1 && p <= totalPages) renderPage(p);
            });
        });
    }

    renderPage(1);
}
