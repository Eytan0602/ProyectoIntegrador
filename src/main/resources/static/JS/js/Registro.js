function buscarDatosVehiculo() {
      const placa = document.getElementById("placa").value.trim();
      if (!placa) return;

      fetch("VehiculoInfoServlet?placa=" + encodeURIComponent(placa))
        .then(res => {
          if (!res.ok) throw new Error("Error al consultar API");
          return res.json();
        })
        .then(data => {
          const vehiculo = data.data || {};
          document.getElementById("marca").value  = vehiculo.marca || "";
          document.getElementById("modelo").value = vehiculo.modelo || "";
          document.getElementById("motor").value  = vehiculo.nro_motor || "";
          document.getElementById("serie").value  = vehiculo.nro_serie || "";
          document.getElementById("vin").value    = vehiculo.vin || "";

          // Guardar en sessionStorage navegador
          sessionStorage.setItem("marca", vehiculo.marca || "");
          sessionStorage.setItem("modelo", vehiculo.modelo || "");
          sessionStorage.setItem("motor", vehiculo.nro_motor || "");
          sessionStorage.setItem("serie", vehiculo.nro_serie || "");
          sessionStorage.setItem("vin", vehiculo.vin || "");
        })
        .catch(err => {
          console.error(err);
          alert("No se pudo obtener información del vehículo.");
        });
    }

    window.addEventListener("DOMContentLoaded", () => {
      ["placa","marca","modelo","anio","motor","serie","vin"].forEach(id => {
        const val = sessionStorage.getItem(id);
        if (val) document.getElementById(id).value = val;
      });
    });