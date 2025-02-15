package Model;

public class CompraModel {
  private int id;
  private int numeroDeCompra;
  private String usuarioVenta;
  private String fechaDeVenta;
  private int usuarioId;
  private int libroId;

  // Constructor
  public CompraModel(int id, int numeroDeCompra, String usuarioVenta, String fechaDeVenta, int usuarioId, int libroId) {
    this.id = id; // Este es el ID generado por la base de datos
    this.numeroDeCompra = numeroDeCompra;
    this.usuarioVenta = usuarioVenta;
    this.fechaDeVenta = fechaDeVenta;
    this.usuarioId = usuarioId;
    this.libroId = libroId;
  }

  public CompraModel( int numeroDeCompra, String usuarioVenta, String fechaDeVenta, int usuarioId, int libroId) {
    this.numeroDeCompra = numeroDeCompra;
    this.usuarioVenta = usuarioVenta;
    this.fechaDeVenta = fechaDeVenta;
    this.usuarioId = usuarioId;
    this.libroId = libroId;
  }

  public CompraModel() {

  }

  // Getters y Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getNumeroDeCompra() {
    return numeroDeCompra;
  }

  public void setNumeroDeCompra(int numeroDeCompra) {
    this.numeroDeCompra = numeroDeCompra;
  }

  public String getUsuarioVenta() {
    return usuarioVenta;
  }

  public void setUsuarioVenta(String usuarioVenta) {
    this.usuarioVenta = usuarioVenta;
  }

  public String getFechaDeVenta() {
    return fechaDeVenta;
  }

  public void setFechaDeVenta(String fechaDeVenta) {
    this.fechaDeVenta = fechaDeVenta;
  }

  public int getUsuarioId() {
    return usuarioId;
  }

  public void setUsuarioId(int usuarioId) {
    this.usuarioId = usuarioId;
  }

  public int getLibroId() {
    return libroId;
  }

  public void setLibroId(int libroId) {
    this.libroId = libroId;
  }

  @Override
  public String toString() {
    return "CompraModel{" +
        "id=" + id +
        ", numeroDeCompra=" + numeroDeCompra +
        ", usuarioVenta='" + usuarioVenta + '\'' +
        ", fechaDeVenta='" + fechaDeVenta + '\'' +
        ", usuarioId=" + usuarioId +
        ", libroId=" + libroId +
        '}';
  }
}


