package Model;

public class LibroModel {
  private int id;
  private int precio;
  private String titulo;
  private String autor;
  private String genero;
  private int ISBN;
  private String categoria;
  private String estado;

  // Constructor
  public LibroModel(int id, String titulo, String autor, String genero, int ISBN, int precio, String categoria, String estado) {
    this.id = id;
    this.titulo = titulo;
    this.autor = autor;
    this.genero = genero;
    this.ISBN = ISBN;
    this.precio = precio;
    this.categoria = categoria;
    this.estado = estado;
  }

  public LibroModel(String titulo, String autor, String genero, int ISBN, int precio, String categoria, String estado) {
    this.titulo = titulo;
    this.autor = autor;
    this.genero = genero;
    this.ISBN = ISBN;
    this.precio = precio;
    this.categoria = categoria;
    this.estado = estado;
  }

  public LibroModel(String titulo, String autor, int precio) {
    this.titulo = titulo;
    this.autor = autor;
    this.precio = precio;
  }

  // Getters y Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getAutor() {
    return autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public int getISBN() {
    return ISBN;
  }

  public void setISBN(int ISBN) {
    this.ISBN = ISBN;
  }

  public int getPrecio() {
    return precio;
  }

  public void setPrecio(int precio) {
    this.precio = precio;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "LibroModel{" +
        "id=" + id +
        ", titulo='" + titulo + '\'' +
        ", autor='" + autor + '\'' +
        ", genero='" + genero + '\'' +
        ", ISBN=" + ISBN +
        ", precio=" + precio +
        ", categoria='" + categoria + '\'' +
        ", estado='" + estado + '\'' +
        '}';
  }
}

