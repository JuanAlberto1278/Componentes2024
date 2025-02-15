package Controller;

import ConexionSql.conexion;
import DAOs.CompraDAO;
import DAOs.LibroDAO;
import Model.CompraModel;
import View.ConsoleView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CompraController {
  private ConsoleView viewConsole;
  private CompraDAO compraDAO;
  private LibroDAO libroDAO;


  public CompraController(ConsoleView viewConsole) {
    this.viewConsole = viewConsole;
    Connection connection = conexion.getConnection();
    this.compraDAO = new CompraDAO(connection);
  }

  public void agregarCompra(int numeroDeCompra, String usuarioVenta, String fechaDeVenta, int usuarioId, int libroId) throws SQLException{
    CompraModel compra = new CompraModel(numeroDeCompra, usuarioVenta, fechaDeVenta, usuarioId, libroId); // ID es generado por la base de datos
    try {
      compraDAO.agregarCompra(compra);
      viewConsole.showMessage("Compra agregada correctamente");
    } catch (SQLException e) {
      viewConsole.errorMessage("Error al agregar compra: " + e.getMessage());
    }
  }


  public void updateCompra(int id, int numeroDeCompra, String usuarioVenta, String fechaDeVenta, int usuarioId, int libroId) {
    CompraModel compra = new CompraModel(id, numeroDeCompra, usuarioVenta, fechaDeVenta, usuarioId, libroId);
    try {
      compraDAO.actualizarCompra(compra);
      viewConsole.showMessage("Actualización de compra correcta");
    } catch (SQLException e) {
      viewConsole.errorMessage("Error al actualizar compra: " + e.getMessage());
    }
  }

  public void eliminarCompra(int id) {
    try {
      compraDAO.eliminarCompra(id);
      viewConsole.showMessage("Eliminación de compra correcta");
    } catch (SQLException e) {
      viewConsole.errorMessage("Error al eliminar compra: " + e.getMessage());
    }
  }

  public void consultarCompra(int id) {
    try {
      CompraModel compra = compraDAO.consultarCompra(id);
      if (compra != null) {
        viewConsole.showCompra(compra);
      } else {
        viewConsole.showMessage("Compra no encontrada");
      }
    } catch (SQLException e) {
      viewConsole.errorMessage("Error al consultar la compra: " + e.getMessage());
    }
  }

  public List<CompraModel> consultarTodasLasCompras() throws SQLException {
    return compraDAO.consultarTodasLasCompras();
  }

  private int generarNumeroDeCompra() {
    // Implementa aquí la lógica para generar un número único de compra
    return (int) (Math.random() * 100000); // Ejemplo simple
  }
}


