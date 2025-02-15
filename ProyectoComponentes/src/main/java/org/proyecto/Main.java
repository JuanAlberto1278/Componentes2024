package org.proyecto;

import Controller.LibroController;
import Controller.UsuarioController;
import Controller.CompraController;
import Model.CompraModel;
import Model.LibroModel;
import Model.UsuarioModel;
import View.ConsoleView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws SQLException {
    ConsoleView viewConsole = new ConsoleView();
    CompraController compraController = new CompraController(viewConsole);
    LibroController libroController = new LibroController(viewConsole);
    UsuarioController usuarioController = new UsuarioController(viewConsole);

    Scanner scanner = new Scanner(System.in);
    UsuarioModel usuarioActual = null;

    while (true) {
      if (usuarioActual == null) {
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar Sesión");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
          case 1:
            registrarUsuario(scanner, usuarioController);
            break;
          case 2:
            usuarioActual = iniciarSesion(scanner, usuarioController);
            if (usuarioActual != null) {
              menuPrincipal(scanner, usuarioActual, libroController, compraController, usuarioController, viewConsole);
            }
            break;
          case 3:
            System.out.println("Saliendo del programa...");
            scanner.close();
            return;
          default:
            System.out.println("Opción no válida. Intente de nuevo.");
        }
      }
    }
  }

  private static int leerOpcion(Scanner scanner) {
    int opcion;
    while (true) {
      try {
        opcion = Integer.parseInt(scanner.nextLine());
        return opcion;
      } catch (NumberFormatException e) {
        System.out.println("Entrada no válida. Ingrese un número.");
      }
    }
  }

  private static void registrarUsuario(Scanner scanner, UsuarioController usuarioController) throws SQLException {
    System.out.print("Ingrese su nombre: ");
    String nombre = scanner.nextLine();

    System.out.print("Ingrese su apellido: ");
    String apellido = scanner.nextLine();

    System.out.print("Ingrese su correo: ");
    String correo = scanner.nextLine();

    System.out.print("Ingrese su contraseña: ");
    String contrasena = scanner.nextLine();

    String rol = "Comprador";

    System.out.print("Ingrese su género: ");
    String genero = scanner.nextLine();

    System.out.print("Ingrese su fecha de nacimiento (YYYY-MM-DD): ");
    LocalDate fechaNacimiento;
    while (true) {
      try {
        fechaNacimiento = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
        break;
      } catch (DateTimeParseException e) {
        System.out.println("Fecha inválida. Por favor, ingrese la fecha en el formato YYYY-MM-DD:");
      }
    }

    System.out.print("Ingrese su ID física:");
    int idFisica = scanner.nextInt();

    System.out.print("Ingrese su edad:");
    int edad = scanner.nextInt();
    scanner.nextLine();

    usuarioController.agregarUsuario(nombre, apellido, correo, contrasena, rol, genero, fechaNacimiento, edad, idFisica);
    // nombre, apellido, correo, contrasena, rol, genero, fechaNacimiento, edad, idFisica
  }

  private static UsuarioModel iniciarSesion(Scanner scanner, UsuarioController usuarioController) {
    System.out.print("Ingrese correo: ");
    String correo = scanner.nextLine();
    System.out.print("Ingrese contraseña: ");
    String contrasena = scanner.nextLine();

    UsuarioModel usuario = usuarioController.iniciarSesion(correo, contrasena);
    if (usuario != null) {
      System.out.println("Inicio de sesión exitoso. Bienvenido " + usuario.getNombre());
      return usuario;
    } else {
      System.out.println("Inicio de sesión fallido. Intente de nuevo.");
      return null;
    }
  }


  //Menu Comprador
  private static void menuPrincipal(Scanner scanner, UsuarioModel usuario, LibroController libroController, CompraController compraController, UsuarioController usuarioController, ConsoleView viewConsole) throws SQLException {
    while (true) {
      System.out.println("Menú Principal:");
      if ("comprador".equals(usuario.getRol())) {
        menuComprador(scanner, libroController, compraController, viewConsole, usuario);
      } else if ("administrador".equals(usuario.getRol())) {
        menuAdministrador(scanner, libroController, compraController, usuarioController, viewConsole);
      } else if ("vendedor".equals(usuario.getRol())) {
        menuVendedor(scanner, libroController, compraController, viewConsole, usuario);
      }
      System.out.println("4. Salir");
      System.out.print("Seleccione una opción: ");
      int opcionPrincipal = scanner.nextInt();
      scanner.nextLine();

      if (opcionPrincipal == 4) {
        System.out.println("Cerrando sesión...");
        break;
      }
    }
  }


  private static void menuComprador(Scanner scanner, LibroController libroController, CompraController compraController, ConsoleView viewConsole, UsuarioModel usuarioActual) throws SQLException {
    System.out.println("Menú Comprador:");
    System.out.println("1. Buscar libro por título");
    System.out.println("2. Buscar libros por autor");
    System.out.println("3. Buscar libros por género");

    int opcion = leerOpcion(scanner);

    switch (opcion) {
      case 1:
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();
        LibroModel libro = libroController.buscarLibroPorTitulo(titulo);
        if (libro != null) {
          System.out.println("Libro encontrado: " + libro.getTitulo() + " - " + libro.getAutor());
        } else {
          System.out.println("No se encontró ningún libro con ese título.");
        }
        break;

      case 2:
        System.out.print("Ingrese el autor: ");
        String autor = scanner.nextLine();
        List<LibroModel> librosPorAutor = libroController.buscarLibrosPorAutor(autor);
        if (librosPorAutor.isEmpty()) {
          System.out.println("No se encontraron libros de este autor.");
        } else {
          System.out.println("Libros de " + autor + ":");
          librosPorAutor.forEach(libroItem -> System.out.println(libroItem.getTitulo()));
        }
        break;

      case 3:
        System.out.print("Ingrese el género: ");
        String genero = scanner.nextLine();
        List<LibroModel> librosPorGenero = libroController.buscarLibrosPorGenero(genero);
        if (librosPorGenero.isEmpty()) {
          System.out.println("No se encontraron libros para este género.");
        } else {
          System.out.println("Libros del género " + genero + ":");
          librosPorGenero.forEach(libroItem -> System.out.println(libroItem.getTitulo()));
        }
        break;
      default:
        System.out.println("Opción no válida. Intente de nuevo.");
    }
  }

  //Menu Administrador

  private static void menuAdministrador(Scanner scanner, LibroController libroController, CompraController compraController, UsuarioController usuarioController, ConsoleView viewConsole) throws SQLException {
    System.out.println("Menú Administrador:");
    System.out.println("1. Registrar libro");
    System.out.println("2. Ver inventario");
    System.out.println("3. Eliminar libro");
    System.out.println("4. Actualizar libro");
    System.out.println("5. Buscar libro por titulo");
    System.out.println("6. Buscar libro por autor");
    System.out.println("7. Buscar libro por genero");
    System.out.println("8. Ver usuarios");
    System.out.println("9. Ver todas las compras");

    int opcion = leerOpcion(scanner);

    switch (opcion) {
      case 1:
        agregarLibro(scanner, libroController);
        break;
      case 2:
        consultarTodosLosLibros(scanner, libroController); // Llama al nuevo método
        break;
      case 3:
        eliminarLibro(scanner, libroController);
        break;
      case 4:
        actualizarLibro(scanner, libroController);
        break;
      case 5:
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();
        LibroModel libro = libroController.buscarLibroPorTitulo(titulo);
        if (libro != null) {
          System.out.println("Libro encontrado: " + libro.getTitulo() + " - " + libro.getAutor());
        } else {
          System.out.println("No se encontró ningún libro con ese título.");
        }
        break;
      case 6:
        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();
        List<LibroModel> librosPorAutor = libroController.buscarLibrosPorAutor(autor);
        if (librosPorAutor.isEmpty()) {
          System.out.println("No se encontraron libros de este autor.");
        } else {
          System.out.println("Libros de " + autor + ":");
          librosPorAutor.forEach(libroItem -> System.out.println(libroItem.getTitulo()));
        }
        break;

      case 7:
        System.out.print("Ingrese el género del libro: ");
        String genero = scanner.nextLine();
        List<LibroModel> librosPorGenero = libroController.buscarLibrosPorGenero(genero);
        if (librosPorGenero.isEmpty()) {
          System.out.println("No se encontraron libros para este género.");
        } else {
          System.out.println("Libros del género " + genero + ":");
          librosPorGenero.forEach(libroItem -> System.out.println(libroItem.getTitulo()));
        }
        break;
      case 8:
        consultarUsuarios(scanner,usuarioController);
        break;
      case 9:
        mostrarTodasLasCompras(compraController);
        break;
      default:
        System.out.println("Opción no válida. Intente de nuevo.");
    }
  }

  //Menu Vendedor
  private static void menuVendedor(Scanner scanner, LibroController libroController, CompraController compraController, ConsoleView viewConsole, UsuarioModel usuarioActual) throws SQLException {
    System.out.println("Menú Vendedor:");
    System.out.println("1. Registrar libro");
    System.out.println("2. Ver inventario");
    System.out.println("3. Eliminar libro");
    System.out.println("4. Actualizar libro");
    System.out.println("5. Buscar libro por titulo");
    System.out.println("6. Buscar libro por autor");
    System.out.println("7. Buscar libro por genero");
    System.out.println("8. Registrar compra");

    int opcion = leerOpcion(scanner);

    switch (opcion) {
      case 1:
        agregarLibro(scanner, libroController);
        break;
      case 2:
        consultarTodosLosLibros(scanner, libroController);
        break;
      case 3:
        eliminarLibro(scanner, libroController);
        break;
      case 4:
        actualizarLibro(scanner, libroController);
        break;
      case 5:
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();
        LibroModel libro = libroController.buscarLibroPorTitulo(titulo);
        if (libro != null) {
          System.out.println("Libro encontrado: " + libro.getTitulo() + " - " + libro.getAutor());
        } else {
          System.out.println("No se encontró ningún libro con ese título.");
        }
        break;
      case 6:
        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();
        List<LibroModel> librosPorAutor = libroController.buscarLibrosPorAutor(autor);
        if (librosPorAutor.isEmpty()) {
          System.out.println("No se encontraron libros de este autor.");
        } else {
          System.out.println("Libros de " + autor + ":");
          librosPorAutor.forEach(libroItem -> System.out.println(libroItem.getTitulo()));
        }
        break;

      case 7:
        System.out.print("Ingrese el género del libro: ");
        String genero = scanner.nextLine();
        List<LibroModel> librosPorGenero = libroController.buscarLibrosPorGenero(genero);
        if (librosPorGenero.isEmpty()) {
          System.out.println("No se encontraron libros para este género.");
        } else {
          System.out.println("Libros del género " + genero + ":");
          librosPorGenero.forEach(libroItem -> System.out.println(libroItem.getTitulo()));
        }
        break;
      case 8:
        agregarCompra(scanner, compraController, usuarioActual);
        break;
      default:
        System.out.println("Opción no válida. Intente de nuevo.");
    }
  }

  private static void agregarLibro(Scanner scanner, LibroController libroController) throws SQLException {
    System.out.println("Ingrese los datos del libro:");

    System.out.print("Título: ");
    String titulo = scanner.nextLine();

    System.out.print("Autor: ");
    String autor = scanner.nextLine();

    System.out.print("Género: ");
    String genero = scanner.nextLine();

    System.out.print("ISBN: ");
    int ISBN = scanner.nextInt();

    System.out.print("Precio: ");
    int precio = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Categoría: ");
    String categoria = scanner.nextLine();

    System.out.print("Estado: ");
    String estado = scanner.nextLine();

    libroController.agregarLibro(titulo, autor, genero, ISBN, precio, categoria, estado);
  }

  private static void eliminarLibro(Scanner scanner, LibroController libroController) throws SQLException {
    System.out.print("Ingrese el ID del libro a eliminar: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    libroController.eliminarLibro(id);
  }
  private static void agregarCompra(Scanner scanner, CompraController compraController, UsuarioModel usuarioActual) throws SQLException {
    System.out.print("Ingrese el número de la compra: ");
    int numeroDeCompra = scanner.nextInt();
    scanner.nextLine();  // Limpiar el buffer

    System.out.print("Ingrese el usuario que realiza la venta: ");
    String usuarioVenta = scanner.nextLine();

    System.out.print("Ingrese la fecha de la venta (YYYY-MM-DD): ");
    String fechaDeVenta = scanner.nextLine();

    System.out.print("Ingrese el ID del libro que se va a comprar: ");
    int libroId = scanner.nextInt();
    scanner.nextLine();  // Limpiar el buffer

    System.out.print("Ingrese la cantidad de libros: ");
    int cantidad = scanner.nextInt();
    scanner.nextLine();  // Limpiar el buffer

    // Llamar al método agregarCompra con los parámetros requeridos
    compraController.agregarCompra(numeroDeCompra, usuarioVenta, fechaDeVenta, usuarioActual.getUsuarioId(), libroId);

    System.out.println("Compra registrada exitosamente.");
  }


  private static void consultarTodosLosLibros(Scanner scanner, LibroController libroController) throws SQLException {
    List<LibroModel> libros = libroController.consultarTodosLosLibros();
    if (libros.isEmpty()) {
      System.out.println("No se encontraron libros en la base de datos.");
    } else {
      System.out.println("Lista de todos los libros:");
      for (LibroModel libro : libros) {
        System.out.println(libro.getTitulo() + " - " + libro.getAutor() + " - " + libro.getPrecio());
      }
    }
  }

  private static void actualizarLibro(Scanner scanner, LibroController libroController) {
    System.out.print("Ingrese el ID del libro a actualizar: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Ingrese el nuevo título del libro: ");
    String titulo = scanner.nextLine();

    System.out.print("Ingrese el nuevo autor del libro: ");
    String autor = scanner.nextLine();

    System.out.print("Ingrese el nuevo género del libro: ");
    String genero = scanner.nextLine();

    System.out.print("Ingrese el nuevo ISBN del libro: ");
    int ISBN = scanner.nextInt();

    System.out.print("Ingrese el nuevo precio del libro: ");
    int precio = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Ingrese la nueva categoría del libro: ");
    String categoria = scanner.nextLine();

    System.out.print("Ingrese el nuevo estado del libro: ");
    String estado = scanner.nextLine();

    libroController.actualizarLibro(id, titulo, autor, genero, ISBN, precio, categoria, estado);
  }

  private static void consultarUsuarios(Scanner scanner, UsuarioController usuarioController) throws SQLException {
    System.out.print("Ingrese el ID del usuario para consultar: ");
    int usuarioId = scanner.nextInt();
    scanner.nextLine();
    usuarioController.consultarUsuario(usuarioId);
  }

  // Dentro de tu clase Main
  private static void mostrarTodasLasCompras(CompraController compraController) {
    try {
      List<CompraModel> todasLasCompras = compraController.consultarTodasLasCompras();
      if (todasLasCompras.isEmpty()) {
        System.out.println("No hay compras registradas.");
      } else {
        System.out.println("Compras registradas:");
        for (CompraModel compra : todasLasCompras) {
          System.out.println("ID: " + compra.getId() +
                  ", Número de Compra: " + compra.getNumeroDeCompra() +
                  ", Usuario de Venta: " + compra.getUsuarioVenta() +
                  ", Fecha de Venta: " + compra.getFechaDeVenta() +
                  ", Usuario ID: " + compra.getUsuarioId() +
                  ", Libro ID: " + compra.getLibroId());
        }
      }
    } catch (SQLException e) {
      System.out.println("Error al consultar todas las compras: " + e.getMessage());
    }
  }


}