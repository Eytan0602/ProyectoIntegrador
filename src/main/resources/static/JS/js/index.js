
function toggleTheme() {
      const html = document.documentElement;
      const currentTheme = html.getAttribute('data-theme');
      const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
      
      html.setAttribute('data-theme', newTheme);
      localStorage.setItem('theme', newTheme);
      
      const icon = document.getElementById('themeIcon');
      icon.className = newTheme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
    }

    // Load saved theme
    window.addEventListener('DOMContentLoaded', () => {
      const savedTheme = localStorage.getItem('theme') || 'light';
      document.documentElement.setAttribute('data-theme', savedTheme);
      
      const icon = document.getElementById('themeIcon');
      icon.className = savedTheme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
    });

    // Mobile Menu Toggle
    const btn = document.getElementById('btnMenu');
    const menu = document.getElementById('main-menu');
    
    btn?.addEventListener('click', () => {
      const open = menu.classList.toggle('open');
      btn.classList.toggle('active');
      btn.setAttribute('aria-expanded', open ? 'true' : 'false');
    });

    // Close menu when clicking outside
    document.addEventListener('click', (e) => {
      if (!btn.contains(e.target) && !menu.contains(e.target)) {
        menu.classList.remove('open');
        btn.classList.remove('active');
        btn.setAttribute('aria-expanded', 'false');
      }
    });

    // Close menu when clicking on a link
    menu.querySelectorAll('a').forEach(link => {
      link.addEventListener('click', () => {
        menu.classList.remove('open');
        btn.classList.remove('active');
        btn.setAttribute('aria-expanded', 'false');
      });
    });

    // Search function
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
        // Reset all cards
        cards.forEach(card => card.style.display = "block");
      }
    }

    // Smooth scroll
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
      anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
          target.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
      });
    });
