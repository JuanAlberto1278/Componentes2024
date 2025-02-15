package Controller;

import ConexionSql.conexion;
import DAOs.UsuarioDAO;
import Model.UsuarioModel;
import View.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class UsuarioController {
    private ConsoleView viewConsole;
    private UsuarioDAO usuarioDAO;

    public UsuarioController(ConsoleView viewConsole) {
        this.viewConsole = viewConsole;
        Connection connection = conexion.getConnection();
        this.usuarioDAO = new UsuarioDAO(connection);
    }

    public void agregarUsuario(String nombre, String apellido, String correo, String contrasena, String rol,String genero, LocalDate fechaNacimiento, int edad, int idFisica) throws SQLException{
        UsuarioModel datos = new UsuarioModel(nombre, apellido, correo, contrasena, rol, genero, fechaNacimiento, edad, idFisica);
        try {
            usuarioDAO.agregarUsuario(datos);
            viewConsole.showMessage("Inserción de datos correcta");
        } catch (SQLException e) {
            viewConsole.errorMessage("Error al insertar datos: " + e.getMessage());
        }
    }

    public void actualizarUsuario(String nombre, String apellido, String correo, String contrasena, String rol, String genero, LocalDate fechaNacimiento, int edad, int idFisica) {
        UsuarioModel datos = new UsuarioModel(nombre, apellido, correo, contrasena, rol, genero, fechaNacimiento, edad, idFisica);
        try {
            usuarioDAO.actualizarUsuario(datos);
            viewConsole.showMessage("Usuario actualizado correctamente");
        } catch (SQLException e) {
            viewConsole.errorMessage("Error al actualizar usuario: " + e.getMessage());
        }
    }

    public void eliminarUsuario(int id) {
        try {
            usuarioDAO.eliminarUsuario(id);
            viewConsole.showMessage("Usuario eliminado correctamente");
        } catch (SQLException e) {
            viewConsole.errorMessage("Error al eliminar usuario: " + e.getMessage());
        }
    }

    public UsuarioModel consultarUsuario(int usuarioId) throws SQLException {
        try {
            UsuarioModel usuario = usuarioDAO.consultarUsuario(usuarioId);
            if (usuario != null) {
                viewConsole.showUsuario(usuario);
                return usuario;
            } else {
                viewConsole.showMessage("Usuario no encontrado");
                return null;
            }
        } catch (SQLException e) {
            viewConsole.errorMessage("Error al consultar usuario: " + e.getMessage());
            throw e; // Lanzar excepción para manejarla en la GUI
        }
    }

    public UsuarioModel iniciarSesion(String correo, String contrasena) {
        try {
            return usuarioDAO.iniciarSesion(correo, contrasena);
        } catch (SQLException e) {
            viewConsole.errorMessage("Error al iniciar sesión: " + e.getMessage());
            return null;
        }
    }
}
