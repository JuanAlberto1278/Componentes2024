package DAOs;

import Model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UsuarioDAO {
  private Connection connection;
  public UsuarioDAO(Connection connection) {
    this.connection = connection;
  }

  public void agregarUsuario(UsuarioModel usuario) throws SQLException {
    String query = "INSERT INTO `JA_DR_AP_usuario` (nombre, apellido, correo, contrasena, rol, genero, fechaNacimiento, edad, idFisica) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, usuario.getNombre());
      stmt.setString(2, usuario.getApellido());
      stmt.setString(3, usuario.getCorreo());
      stmt.setString(4, usuario.getContrasena());
      stmt.setString(5, usuario.getRol());
      stmt.setString(6, usuario.getGenero());
      stmt.setDate(7, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
      stmt.setInt(8, usuario.getEdad());
      stmt.setInt(9, usuario.getIdFisica());
      stmt.executeUpdate();
    }
  }

  public void actualizarUsuario(UsuarioModel objeto) throws SQLException {
    String query = "UPDATE `JA_DR_AP_usuario` SET `nombre` = ?, `apellido` = ?, `contrasena` = ?, `correo` = ?, `edad` = ?, `fechaNacimiento` = ?, `rol` = ?, `genero` = ? WHERE `id` = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, objeto.getNombre());
      stmt.setString(2, objeto.getApellido());
      stmt.setString(3, objeto.getContrasena());
      stmt.setString(4, objeto.getCorreo());
      stmt.setInt(5, objeto.getEdad());
      stmt.setDate(6, java.sql.Date.valueOf(objeto.getFechaNacimiento()));
      stmt.setString(7, objeto.getRol());
      stmt.setString(8, objeto.getGenero());
      stmt.setInt(9, objeto.getUsuarioId());
      stmt.executeUpdate();
    }
  }

  public void eliminarUsuario(int id) throws SQLException {
    String query = "DELETE FROM `JA_DR_AP_usuario` WHERE `id` = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    }
  }

  public UsuarioModel consultarUsuario(int usuarioId) throws SQLException {
    String query = "SELECT `id`, `nombre`, `apellido`, `correo`, `contrasena`, `rol`, `genero`, `fechaNacimiento`, `edad`, `idFisica` FROM `JA_DR_AP_usuario` WHERE `id` = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, usuarioId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int Id = rs.getInt("Id");
          String nombre = rs.getString("nombre");
          String apellido = rs.getString("apellido");
          String correo = rs.getString("correo");
          String contrasena = rs.getString("contrasena");
          String rol = rs.getString("rol");
          String genero = rs.getString("genero");
          LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();
          int edad = rs.getInt("edad");
          int idFisica = rs.getInt("idFisica");

          return new UsuarioModel(Id, nombre, apellido, correo, contrasena, rol, genero, fechaNacimiento, edad, idFisica);
        } else {
          return null;
        }
      }
    }
  }
  public UsuarioModel consultarUsuarioPorCorreo(String correo) throws SQLException {
    String query = "SELECT `id`, `nombre`, `apellido`, `correo`, `contrasena`, `rol`, `genero`, `fechaNacimiento`, `edad`, `idFisica` FROM `JA_DR_AP_usuario` WHERE `correo` = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, correo);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int id = rs.getInt("id");
          String nombre = rs.getString("nombre");
          String apellido = rs.getString("apellido");
          String contrasena = rs.getString("contrasena");
          String rol = rs.getString("rol");
          String genero = rs.getString("genero");
          LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();
          int edad = rs.getInt("edad");
          int idFisica = rs.getInt("idFisica");
          return new UsuarioModel(id, nombre, apellido, correo, contrasena, rol, genero, fechaNacimiento, edad, idFisica);
        } else {
          return null;
        }
      }
    }
  }
  public UsuarioModel iniciarSesion(String correo, String contrasena) throws SQLException {
    String query = "SELECT `id`, `nombre`, `apellido`, `correo`, `contrasena`, `rol`, `genero`, `fechaNacimiento`, `edad`, `idFisica` FROM `JA_DR_AP_usuario` WHERE `correo` = ? AND `contrasena` = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, correo);
      stmt.setString(2, contrasena);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int id = rs.getInt("id");
          String nombre = rs.getString("nombre");
          String apellido = rs.getString("apellido");
          String rol = rs.getString("rol");
          String genero = rs.getString("genero");
          LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();
          int edad = rs.getInt("edad");
          int idFisica = rs.getInt("idFisica");
          return new UsuarioModel(id, nombre, apellido, correo, contrasena, rol, genero, fechaNacimiento, edad, idFisica);
        } else {
          return null;
        }
      }
    }
  }

}
