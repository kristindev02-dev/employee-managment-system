(function(){
    function ensureContainer(){
        let container = document.getElementById('toast-container');
        if (!container){
            container = document.createElement('div');
            container.id = 'toast-container';
            container.setAttribute('aria-live','polite');
            container.setAttribute('aria-atomic','true');
            document.body.appendChild(container);
        } else if (container.parentElement !== document.body){
            document.body.appendChild(container);
        }
        return container;
    }

    function createAlert(type, message){
        const alert = document.createElement('div');
        alert.className = `alert alert-${type} alert-dismissible shadow-sm toast-in`;
        alert.setAttribute('role', 'alert');
        alert.setAttribute('aria-live', 'polite');
        alert.innerHTML = `
            <div class="d-flex justify-content-between align-items-start gap-3">
                <div>${message}</div>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;
        alert.style.pointerEvents = 'auto';
        return alert;
    }

    function showFlashMessage(type, message, autoDismissMs = 5000){
        try {
            const container = ensureContainer();
            const alert = createAlert(type, message);
            container.appendChild(alert);

            // Auto-dismiss
            const timeoutId = setTimeout(() => {
                // add exit animation class and remove after animation
                alert.classList.remove('toast-in');
                alert.classList.add('toast-out');
                setTimeout(() => {
                    alert.remove();
                    clearTimeout(timeoutId);
                }, 220);
            }, autoDismissMs);

            return alert;
        } catch (err){
            console.error('Toast error', err);
            return null;
        }
    }

    // Expose as window.flash.showFlashMessage and window.showFlashMessage for compatibility
    window.flash = window.flash || {};
    window.flash.showFlashMessage = showFlashMessage;
    window.showFlashMessage = showFlashMessage;
})();
