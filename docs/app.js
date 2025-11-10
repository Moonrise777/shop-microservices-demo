const API_URL = "https://product-api-wn79.onrender.com";

// (La parte que tiene 'simulatePurchase' y 'showToast')
document.addEventListener("DOMContentLoaded", () => {
    const buyButtons = document.querySelectorAll(".buy-btn");
    
    buyButtons.forEach(button => {
        button.addEventListener("click", () => {
            const id = button.getAttribute("data-id");
            const name = button.getAttribute("data-name");
            simulatePurchase(id, name);
        });
    });
});

async function simulatePurchase(id, name) {
    const productData = { id, name };
    try {
        const response = await fetch(`${API_URL}/products`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(productData)
        });
        if (response.status === 201) {
            const result = await response.json();
            showToast(`¡Evento enviado para ${result.name}! Procesando...`);
        } else {
            showToast("Error de la API. (¿El backend está desplegado?)", true);
        }
    } catch (error) {
        console.error("Error de fetch:", error);
        showToast("Error de conexión. Revisa la URL de la API.", true);
    }
}
function showToast(message, isError = false) {
    const toast = document.getElementById("toast");
    toast.textContent = message;
    toast.className = "toast show";
    if (isError) {
        toast.classList.add("error");
    }
    setTimeout(() => {
        toast.className = toast.className.replace("show", "");
        if (isError) {
            toast.classList.remove("error");
        }
    }, 3000);
}