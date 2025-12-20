document.addEventListener("DOMContentLoaded", () => {
    loadProducts("all");

    document.querySelectorAll(".filter-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            document.querySelectorAll(".filter-btn")
                .forEach(b => b.classList.remove("active"));

            btn.classList.add("active");
            loadProducts(btn.dataset.type);
        });
    });
});

// =======================
// FETCH PRODUITS
// =======================
function loadProducts(type) {
    fetch(`/Home/Filter?type=${type}`)
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById("productsContainer");
            container.innerHTML = "";

            if (type === "all") {
                renderList(data.menus, "MENU", "menus");
                renderList(data.burgers, "BURGER", "burgers");
                renderList(data.complements, "COMPLÉMENT", "complements");
            } else {
                renderList(data, type.toUpperCase(), type);
            }

            animateCards();
        });
}

// =======================
// RENDER HTML
// =======================
function renderList(items, label, type) {
    const container = document.getElementById("productsContainer");

    items.forEach(item => {
        container.innerHTML += `
            <div class="product-card">
                <img src="${item.image ?? ''}" class="product-image" alt="${item.nom}">
                <div class="product-info">
                    <span class="product-badge">${label}</span>
                    <h3 class="product-name">${item.nom}</h3>

                    ${item.prix ? `
                        <div class="product-footer">
                            <span class="product-price">${item.prix} FCFA</span>
                            <button class="btn-add add-to-cart"
                                data-type="${type}"
                                data-id="${item.id}"
                                data-nom="${item.nom}"
                                data-prix="${item.prix}"
                                data-image="${item.image ?? ''}">
                                <i class="fas fa-plus"></i>
                                Ajouter
                            </button>
                        </div>
                    ` : ""}
                </div>
            </div>
        `;
    });
}

// =======================
// ANIMATIONS SCROLL
// =======================
function animateCards() {
    const observer = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, { threshold: 0.1 });

    document.querySelectorAll('.product-card').forEach(card => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(30px)';
        card.style.transition = 'all 0.6s ease';
        observer.observe(card);
    });
}
