package DAOs;

import Model.CompraModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {
  private Connection connection;

  public CompraDAO(Connection connection) {
    this.connection = connection;
  }

  public void agregarCompra(CompraModel compra) throws SQLException {
    String query = "INSERT INTO JA_DR_AP_compra(NumeroDeCompra, usuarioVenta, FechaDeVenta, usuarioId, libroId) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, compra.getNumeroDeCompra());
      stmt.setString(2, compra.getUsuarioVenta());
      stmt.setString(3, compra.getFechaDeVenta());
      stmt.setInt(4, compra.getUsuarioId());
      stmt.setInt(5, compra.getLibroId());
      stmt.executeUpdate();
    }
  }

  public void actualizarCompra(CompraModel compra) throws SQLException {
    String query = "UPDATE JA_DR_AP_compra SET NumeroDeCompra = ?, usuarioVenta = ?, FechaDeVenta = ?, usuarioId = ?, libroId = ? WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, compra.getNumeroDeCompra());
      stmt.setString(2, compra.getUsuarioVenta());
      stmt.setString(3, compra.getFechaDeVenta());
      stmt.setInt(4, compra.getUsuarioId());
      stmt.setInt(5, compra.getLibroId());
      stmt.setInt(6, compra.getId());
      stmt.executeUpdate();
    }
  }

  public void eliminarCompra(int id) throws SQLException {
    String query = "DELETE FROM JA_DR_AP_compra WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    }
  }

  public CompraModel consultarCompra(int id) throws SQLException {
    String query = "SELECT NumeroDeCompra, usuarioVenta, FechaDeVenta, usuarioId, libroId FROM JA_DR_AP_compra WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int numeroDeCompra = rs.getInt("NumeroDeCompra");
          String usuarioVenta = rs.getString("usuarioVenta");
          String fechaDeVenta = rs.getString("FechaDeVenta");
          int usuarioId = rs.getInt("usuarioId");
          int libroId = rs.getInt("libroId");
          return new CompraModel(id, numeroDeCompra, usuarioVenta, fechaDeVenta, usuarioId, libroId);
        } else {
          return null;
        }
      }
    }
  }
  public List<CompraModel> consultarTodasLasCompras() throws SQLException {
    List<CompraModel> compras = new ArrayList<>();
    String query = "SELECT id, NumeroDeCompra, usuarioVenta, FechaDeVenta, usuarioId, libroId FROM JA_DR_AP_compra";
    try (PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        int id = rs.getInt("id");
        int numeroDeCompra = rs.getInt("NumeroDeCompra");
        String usuarioVenta = rs.getString("usuarioVenta");
        String fechaDeVenta = rs.getString("FechaDeVenta");
        int usuarioId = rs.getInt("usuarioId");
        int libroId = rs.getInt("libroId");
        CompraModel compra = new CompraModel(id, numeroDeCompra, usuarioVenta, fechaDeVenta, usuarioId, libroId);
        compras.add(compra);
      }
    }
    return compras;
  }

}