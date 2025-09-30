 function refreshCaptcha() {
    var captchaImg = document.getElementById("captchaImage");
    captchaImg.src = "CaptchaGenerator?timestamp=" + new Date().getTime();
  }

  document.getElementById('dniForm').addEventListener('submit', function(event) {
    const errors = document.querySelectorAll('.error-message');
    errors.forEach(e => { e.style.display = 'none'; e.textContent = ''; });
    let valid = true;

    const dni = document.getElementById('dni');
    if (!/^\d{8}$/.test(dni.value.trim())) {
      document.getElementById('dniError').textContent = "Ingrese un DNI válido de 8 dígitos.";
      document.getElementById('dniError').style.display = 'block';
      valid = false;
    }

    const apellidos = document.getElementById('apellidos_nombres');
    if (apellidos.value.trim().length < 3) {
      document.getElementById('apellidosError').textContent = "Ingrese apellidos y nombres válidos.";
      document.getElementById('apellidosError').style.display = 'block';
      valid = false;
    }

    const celular = document.getElementById('celular');
    if (!/^\d{9}$/.test(celular.value.trim())) {
      document.getElementById('celularError').textContent = "Ingrese un celular válido de 9 dígitos.";
      document.getElementById('celularError').style.display = 'block';
      valid = false;
    }

     const form = document.getElementById('dniForm');
  form.addEventListener('submit', function(event) {
    const captchaInput = document.getElementById('captchaInput');
    const errorDiv = document.getElementById('captchaError');
    errorDiv.style.display = 'none';
    errorDiv.textContent = '';

    if (captchaInput.value.trim() === '') {
      event.preventDefault();
      errorDiv.textContent = 'Ingrese el código CAPTCHA.';
      errorDiv.style.display = 'block';
    }
  });
    
  });
