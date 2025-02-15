package View;

import Model.CompraModel;
import Model.LibroModel;
import Model.UsuarioModel;

public class ConsoleView {
  public void showMessage(String message){
    System.out.println(message);
  }

  public void errorMessage(String message){
    System.err.println(message);
  }

  public void showLibro(LibroModel libro) {
    System.out.println("Id: " + libro.getId());
    System.out.println("Autor: " + libro.getAutor());
    System.out.println("Titulo: " + libro.getTitulo());
    System.out.println("Genero: " + libro.getGenero());
    System.out.println("ISBN: " + libro.getISBN());
    System.out.println("Precio: " + libro.getPrecio());
    System.out.println("Categoria: " + libro.getCategoria());
  }

  public void showUsuario(UsuarioModel usuario) {
    System.out.println("Id: " + usuario.getUsuarioId());
    System.out.println("Nombre: " + usuario.getNombre());
    System.out.println("Apellido: " + usuario.getApellido());
    System.out.println("Correo: " + usuario.getCorreo() );
    System.out.println("Contrasena: " + usuario.getContrasena());
    System.out.println("Rol: " + usuario.getRol());
    System.out.println("fechaNacimiendo: " + usuario.getFechaNacimiento());
    System.out.println("Edad: " + usuario.getEdad());
    System.out.println("Genero: " + usuario.getGenero());
    System.out.println("idFisica: " + usuario.getIdFisica());
  }
  public void showCompra(CompraModel compra) {
    System.out.println("ID de Compra: " + compra.getId());
    System.out.println("Número de Compra: " + compra.getNumeroDeCompra());
    System.out.println("Usuario de Venta: " + compra.getUsuarioVenta());
    System.out.println("Fecha de Venta: " + compra.getFechaDeVenta());
    System.out.println("ID de Usuario: " + compra.getUsuarioId());
    System.out.println("ID de Libro: " + compra.getLibroId());
  }
}
