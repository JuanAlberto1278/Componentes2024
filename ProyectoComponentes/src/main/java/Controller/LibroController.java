package Controller;

import ConexionSql.conexion;
import DAOs.LibroDAO;
import Model.LibroModel;
import Model.UsuarioModel;
import View.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroController {
  private static ConsoleView viewConsole;
  private static LibroDAO libroDAO;

  public LibroController(ConsoleView viewConsole) {
    this.viewConsole = viewConsole;
    Connection connection = conexion.getConnection();
    this.libroDAO = new LibroDAO(connection);
  }
  public LibroController(LibroDAO libroDAO) {
    this.libroDAO = libroDAO;
  }

  public LibroModel buscarLibroPorTitulo(String titulo)throws SQLException  {
    return libroDAO.consultarLibroPorTitulo(titulo);
  }

  public List<LibroModel> buscarLibrosPorAutor(String autor) throws SQLException {
    return libroDAO.consultarLibrosPorAutor(autor);
  }

  public List<LibroModel> buscarLibrosPorGenero(String genero) throws SQLException {
    return libroDAO.consultarLibrosPorGenero(genero);
  }

  public void agregarLibro(String titulo, String autor, String genero, int ISBN, int precio, String categoria, String estado) throws SQLException{
    LibroModel datos = new LibroModel(titulo,  autor,  genero,  ISBN,  precio,  categoria,  estado);
    try {
      libroDAO.agregarLibro(datos);
      viewConsole.showMessage("Libro agregado correctamente");
    } catch (SQLException e) {
      viewConsole.errorMessage("Error al agregar libro: " + e.getMessage());
    }
  }

  public void actualizarLibro(int id, String titulo, String autor, String genero, int ISBN, int precio, String categoria, String estado) {
    LibroModel datos = new LibroModel( id,  titulo,  autor,  genero,  ISBN,  precio,  categoria,  estado);
    try {
      libroDAO.actualizarLibro(datos);
      viewConsole.showMessage("Libro actualizado correctamente");
    } catch (SQLException e) {
      viewConsole.errorMessage("Error al actualizar libro: " + e.getMessage());
    }
  }

  public static List<LibroModel> consultarTodosLosLibros() throws SQLException {
    try {
      return libroDAO.consultarTodosLosLibros();
    } catch (SQLException e) {
      viewConsole.errorMessage("Error al consultar todos los libros: " + e.getMessage());
      return new ArrayList<>();
    }
  }

  public void eliminarLibro(int id) throws SQLException{
    try {
      libroDAO.eliminarLibro(id);
      viewConsole.showMessage("Libro eliminado correctamente");
    } catch (SQLException e) {
      viewConsole.errorMessage("Error al eliminar libro: " + e.getMessage());
    }
  }

  public void consultarLibro(int id) {
    try {
      LibroModel libro = libroDAO.consultarLibro(id);
      if (libro != null) {
        viewConsole.showLibro(libro);
      } else {
        viewConsole.showMessage("Libro no encontrado");
      }
    } catch (SQLException e) {
      viewConsole.errorMessage("Error al consultar libro: " + e.getMessage());
    }
  }
  public boolean realizarCompra(String titulo, int cantidad, int usuarioId) {
    try {
      LibroModel libro = libroDAO.consultarLibroPorTitulo(titulo);
      if (libro != null && libro.getEstado().equals("disponible")) {
        // Lógica para realizar la compra
        return true; // Compra realizada con éxito
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false; // Error al realizar la compra
  }
}
