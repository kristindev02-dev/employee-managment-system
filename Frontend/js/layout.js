document.addEventListener("DOMContentLoaded", () => {
    loadSidebar();
});

async function loadSidebar() {
    try {
        const response = await fetch("sidebar.html");
        if (!response.ok) {
            throw new Error(`Unable to load sidebar: ${response.status} ${response.statusText}`);
        }

        const html = await response.text();
        const template = document.createElement("template");
        template.innerHTML = html.trim();

        const sidebar = template.content.firstElementChild;
        if (!sidebar) return;

        const desktopContainer = document.getElementById("sidebarContainer");
        const offcanvasBody = document.getElementById("offcanvasSidebarBody");

        if (desktopContainer) {
            desktopContainer.innerHTML = "";
            desktopContainer.appendChild(sidebar.cloneNode(true));
        }

        if (offcanvasBody) {
            offcanvasBody.innerHTML = "";
            offcanvasBody.appendChild(sidebar.cloneNode(true));
        }

        activateSidebarLink();
    } catch (error) {
        console.error(error);
    }
}

function activateSidebarLink() {
    const currentPage = window.location.pathname.split("/").pop() || "dashboard.html";
    const links = document.querySelectorAll("#sidebarContainer .nav-link, #offcanvasSidebarBody .nav-link");

    links.forEach(link => {
        const href = link.getAttribute("href")?.split("/").pop();
        if (!href) return;

        if (href === currentPage || (currentPage === "" && href === "dashboard.html")) {
            link.classList.add("active");
        } else {
            link.classList.remove("active");
        }
    });
}
