package com.pnp.portal.controller;

import com.pnp.portal.model.Vehiculo;
import com.pnp.portal.repository.VehiculoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class RegistroVehicularController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @GetMapping("/registro-vehicular")
    public String mostrarFormularioRegistroVehicular(HttpSession session, Model model) {
        model.addAttribute("placa", session.getAttribute("placa"));
        model.addAttribute("marca", session.getAttribute("marca"));
        model.addAttribute("modelo", session.getAttribute("modelo"));
        model.addAttribute("anio", session.getAttribute("anio"));
        model.addAttribute("motor", session.getAttribute("motor"));
        model.addAttribute("serie", session.getAttribute("serie"));
        model.addAttribute("vin", session.getAttribute("vin"));
        return "registro-vehicular";  
    }

    @PostMapping("/registro-vehicular")
    public String procesarFormularioRegistroVehicular(@RequestParam Map<String, String> params, HttpSession session) {
        Vehiculo vehiculo = new Vehiculo();
        
        vehiculo.setPlaca(params.get("placa"));
        vehiculo.setMarca(params.get("marca"));
        vehiculo.setModelo(params.get("modelo"));
        
        if (params.get("anio") != null && !params.get("anio").isEmpty()) {
            vehiculo.setAnio(Integer.parseInt(params.get("anio")));
        }
        
        vehiculo.setMotor(params.get("motor"));
        vehiculo.setSerie(params.get("serie"));
        vehiculo.setVin(params.get("vin"));
        vehiculo.setFotoVerificacion(params.get("fotoVerificacion"));

        // GUARDAR EN BASE DE DATOS
        try {
            vehiculoRepository.save(vehiculo);
            System.out.println("✅ VEHICULO GUARDADO: " + vehiculo.getPlaca());
        } catch (Exception e) {
            System.err.println("❌ ERROR AL GUARDAR VEHICULO: " + e.getMessage());
            e.printStackTrace();
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String val = entry.getValue();
            if (val != null && !val.isEmpty()) {
                session.setAttribute(entry.getKey(), val);
            }
        }
        
        return "redirect:/soat";
    }
}
