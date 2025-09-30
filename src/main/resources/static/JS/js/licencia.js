window.addEventListener("DOMContentLoaded", () => {
  if (!dni) return;

  document.getElementById("dni").value = dni;

  fetch(`/api/licencia-info?dni=${encodeURIComponent(dni)}`)
    .then(res => res.json())
    .then(data => {
      const d = data.data;
      const lic = d.licencia;

      document.getElementById("nombre_completo").value = d.nombre_completo || "";
      document.getElementById("numero_licencia").value = lic.numero || "";
      document.getElementById("categoria").value = lic.categoria || "";
      document.getElementById("fecha_expedicion").value = lic.fecha_expedicion || "";
      document.getElementById("fecha_vencimiento").value = lic.fecha_vencimiento || "";
      document.getElementById("estado").value = lic.estado || "";
      document.getElementById("restricciones").value = lic.restricciones || "";
    })
    .catch(err => console.error("Error en API licencia:", err));
});
