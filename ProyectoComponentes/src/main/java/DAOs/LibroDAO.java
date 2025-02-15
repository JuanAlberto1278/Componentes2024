package DAOs;

import Model.LibroModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
  private Connection connection;

  public LibroDAO(Connection connection) {
    this.connection = connection;
  }

  public void agregarLibro(LibroModel libro) throws SQLException {
    String query = "INSERT INTO `JA_DR_AP_libro`(`titulo`, `autor`, `genero`, `ISBN`, `precio`, `categoria`, `estado`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, libro.getTitulo());
      stmt.setString(2, libro.getAutor());
      stmt.setString(3, libro.getGenero());
      stmt.setInt(4, libro.getISBN());
      stmt.setInt(5, libro.getPrecio());
      stmt.setString(6, libro.getCategoria());
      stmt.setString(7, libro.getEstado());
      stmt.executeUpdate();
    }
  }

  public void actualizarLibro(LibroModel libro) throws SQLException {
    String query = "UPDATE `JA_DR_AP_libro` SET `titulo` = ?, `autor` = ?, `genero` = ?, `ISBN` = ?, `precio` = ?, `categoria` = ?, `estado` = ? WHERE `id` = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, libro.getTitulo());
      stmt.setString(2, libro.getAutor());
      stmt.setString(3, libro.getGenero());
      stmt.setInt(4, libro.getISBN());
      stmt.setInt(5, libro.getPrecio());
      stmt.setString(6, libro.getCategoria());
      stmt.setString(7, libro.getEstado());
      stmt.setInt(8, libro.getId());
      stmt.executeUpdate();
    }
  }

  public void eliminarLibro(int id) throws SQLException {
    String query = "DELETE FROM `JA_DR_AP_libro` WHERE `id` = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    }
  }

  public LibroModel consultarLibro(int id) throws SQLException {
    String query = "SELECT `titulo`, `autor`, `genero`, `ISBN`, `precio`, `categoria`, `estado` FROM `JA_DR_AP_libro` WHERE `id` = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          String titulo = rs.getString("titulo");
          String autor = rs.getString("autor");
          String genero = rs.getString("genero");
          int ISBN = rs.getInt("ISBN");
          int precio = rs.getInt("precio");
          String categoria = rs.getString("categoria");
          String estado = rs.getString("estado");
          return new LibroModel(id, titulo, autor, genero, ISBN, precio, categoria, estado);
        } else {
          return null;
        }
      }
    }
  }

  public LibroModel consultarLibro(String titulo) throws SQLException {
    String query = "SELECT `id`, `autor`, `genero`, `ISBN`, `precio`, `categoria`, `estado` FROM `JA_DR_AP_libro` WHERE `titulo` = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, titulo);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int id = rs.getInt("id");
          String autor = rs.getString("autor");
          String genero = rs.getString("genero");
          int ISBN = rs.getInt("ISBN");
          int precio = rs.getInt("precio");
          String categoria = rs.getString("categoria");
          String estado = rs.getString("estado");
          return new LibroModel(id, titulo, autor, genero, ISBN, precio, categoria, estado);
        } else {
          return null;
        }
      }
    }
  }

  public List<LibroModel> consultarTodosLosLibros() throws SQLException {
    String query = "SELECT `titulo`, `autor`, `precio` FROM `JA_DR_AP_libro`";
    List<LibroModel> libros = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        String titulo = rs.getString("titulo");
        String autor = rs.getString("autor");
        int precio = rs.getInt("precio");
        libros.add(new LibroModel(titulo, autor, precio));
      }
    }
    return libros;
  }

  public LibroModel consultarLibroPorTitulo(String titulo) throws SQLException {
    String query = "SELECT * FROM `JA_DR_AP_libro` WHERE titulo = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, titulo);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return new LibroModel(
              rs.getInt("id"),
              rs.getString("titulo"),
              rs.getString("autor"),
              rs.getString("genero"),
              rs.getInt("ISBN"),
              rs.getInt("precio"),
              rs.getString("categoria"),
              rs.getString("estado")
          );
        }
      }
    }
    return null;
  }

  public List<LibroModel> consultarLibrosPorAutor(String autor) throws SQLException {
    String query = "SELECT * FROM `JA_DR_AP_libro` WHERE autor = ?";
    List<LibroModel> libros = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, autor);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          libros.add(new LibroModel(
              rs.getInt("id"),
              rs.getString("titulo"),
              rs.getString("autor"),
              rs.getString("genero"),
              rs.getInt("ISBN"),
              rs.getInt("precio"),
              rs.getString("categoria"),
              rs.getString("estado")
          ));
        }
      }
    }
    return libros;
  }

  public List<LibroModel> consultarLibrosPorGenero(String genero) throws SQLException {
    String query = "SELECT * FROM `JA_DR_AP_libro` WHERE genero = ?";
    List<LibroModel> libros = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, genero);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          libros.add(new LibroModel(
              rs.getInt("id"),
              rs.getString("titulo"),
              rs.getString("autor"),
              rs.getString("genero"),
              rs.getInt("ISBN"),
              rs.getInt("precio"),
              rs.getString("categoria"),
              rs.getString("estado")
          ));
        }
      }
    }
    return libros;
  }
}

