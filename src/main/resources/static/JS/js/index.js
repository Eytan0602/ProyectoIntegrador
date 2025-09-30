function doSearch() {
  const q = document.getElementById('q').value.trim().toLowerCase();
  if (!q) {
    alert('Ingresa un término de búsqueda.');
    return;
  }

  const cards = document.querySelectorAll(".card");

  let encontrado = false;

  cards.forEach(card => {
    const texto = card.innerText.toLowerCase();
    if (texto.includes(q)) {
      card.style.display = "block";
      encontrado = true;
    } else {
      card.style.display = "none";
    }
  });

  if (!encontrado) {
    alert("No se encontraron resultados para: " + q);
  }
}

    const btn = document.getElementById('btnMenu');
    const menu = document.getElementById('main-menu');
    btn?.addEventListener('click',()=>{
      const open = menu.classList.toggle('open');
      btn.setAttribute('aria-expanded', open? 'true':'false');
    });