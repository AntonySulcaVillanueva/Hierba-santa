package com.eon.hierbasanta.service;

import com.eon.hierbasanta.Util.Constants;
import com.eon.hierbasanta.model.Usuario;
import com.eon.hierbasanta.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsernameAndPassword(username, password);

        if (usuario != null && usuario.getEmpleado() != null) {
            // Guardar la información del empleado en Constants
            Constants.EMPLEADO_LOGUEADO_NOMBRE = usuario.getEmpleado().getNombreCompleto();
            Constants.EMPLEADO_LOGUEADO_CARGO = usuario.getEmpleado().getCargo();
            Constants.EMPLEADO_LOGUEADO_ID = usuario.getEmpleado().getIdEmpleado();
            return true;
        }
        return false;
    }

    public void logout() {
        Constants.EMPLEADO_LOGUEADO_NOMBRE = "";
        Constants.EMPLEADO_LOGUEADO_CARGO = "";
        Constants.EMPLEADO_LOGUEADO_ID = null;
    }

    public static String getEmpleadoLogueadoInfo() {
        return "Nombre: " + Constants.EMPLEADO_LOGUEADO_NOMBRE +
                ", Cargo: " + Constants.EMPLEADO_LOGUEADO_CARGO +
                ", ID: " + Constants.EMPLEADO_LOGUEADO_ID;
    }
}