package org.proyecto;

import Controller.CompraController;
import Controller.LibroController;
import Controller.UsuarioController;
import Model.CompraModel;
import Model.LibroModel;
import Model.UsuarioModel;
import View.ConsoleView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.sql.SQLException;

public class MainGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UsuarioModel usuarioActual;

    private LibroController libroController;
    private CompraController compraController;
    private UsuarioController usuarioController;

    public MainGUI(LibroController libroController, CompraController compraController, UsuarioController usuarioController) {
        this.libroController = libroController;
        this.compraController = compraController;
        this.usuarioController = usuarioController;

        setTitle("Sistema de Gestión de Libros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        add(mainPanel);

        // Agregar paneles
        mainPanel.add(createLoginPanel(), "loginPanel");
        mainPanel.add(createMenuPanel(), "menuPanel");
        mainPanel.add(createMenuCompradorPanel(), "menuCompradorPanel");
        mainPanel.add(createMenuAdministradorPanel(), "menuAdministradorPanel");
        mainPanel.add(createMenuVendedorPanel(), "menuVendedorPanel");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10)); // Añadir separación entre celdas

        panel.add(new JLabel("Correo:"));
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(100, 30)); // Tamaño preferido más compacto
        panel.add(emailField);

        panel.add(new JLabel("Contraseña:"));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 30)); // Tamaño preferido más compacto
        panel.add(passwordField);

        // Crear paneles para los botones con margen
        JPanel loginButtonPanel = createStyledButtonPanel("Iniciar Sesión");
        JPanel registerButtonPanel = createStyledButtonPanel("Registrarse");

        // Obtener los botones de los paneles
        JButton loginButton = (JButton) loginButtonPanel.getComponent(0);
        JButton registerButton = (JButton) registerButtonPanel.getComponent(0);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(emailField.getText(), new String(passwordField.getPassword()));
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterDialog();
            }
        });

        // Agregar los paneles de los botones al panel principal
        panel.add(loginButtonPanel);
        panel.add(registerButtonPanel);

        return panel;
    }
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10)); // Añadir separación entre filas
        JPanel buyerButtonPanel = createStyledButtonPanel("Menú Comprador");
        JPanel adminButtonPanel = createStyledButtonPanel("Menú Administrador");
        JPanel sellerButtonPanel = createStyledButtonPanel("Menú Vendedor");

        JButton buyerButton = (JButton) buyerButtonPanel.getComponent(0);
        JButton adminButton = (JButton) adminButtonPanel.getComponent(0);
        JButton sellerButton = (JButton) sellerButtonPanel.getComponent(0);

        buyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "menuCompradorPanel");
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "menuAdministradorPanel");
            }
        });

        sellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "menuVendedorPanel");
            }
        });

        panel.add(buyerButtonPanel);
        panel.add(adminButtonPanel);
        panel.add(sellerButtonPanel);

        return panel;
    }

    private JPanel createMenuCompradorPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10)); // 4 filas, 1 columna, con separación de 10px

        // Botón para buscar libro por título
        JPanel buscarPorTituloButtonPanel = createStyledButtonPanel("Buscar libro por título");
        JPanel buscarPorAutorButtonPanel = createStyledButtonPanel("Buscar libros por autor");
        JPanel buscarPorGeneroButtonPanel = createStyledButtonPanel("Buscar libros por género");

        JButton buscarPorTituloButton = (JButton) buscarPorTituloButtonPanel.getComponent(0);
        JButton buscarPorAutorButton = (JButton) buscarPorAutorButtonPanel.getComponent(0);
        JButton buscarPorGeneroButton = (JButton) buscarPorGeneroButtonPanel.getComponent(0);

        buscarPorTituloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLibroPorTitulo();
            }
        });

        buscarPorAutorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLibrosPorAutor();
            }
        });

        buscarPorGeneroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLibrosPorGenero();
            }
        });

        // Agregar botones al panel
        panel.add(buscarPorTituloButtonPanel);
        panel.add(buscarPorAutorButtonPanel);
        panel.add(buscarPorGeneroButtonPanel);

        return panel;
    }

    private JPanel createInputPanel(String label) {
        JPanel panel = new JPanel(new FlowLayout());
        JTextField inputField = new JTextField(20); // Tamaño del campo de texto
        panel.add(new JLabel(label));
        panel.add(inputField);
        return panel;
    }

    private JPanel createMessagePanel(String message) {
        JPanel panel = new JPanel(new FlowLayout());
        JLabel messageLabel = new JLabel(message);
        panel.add(messageLabel);
        return panel;
    }

    private void buscarLibroPorTitulo() {
        JPanel inputPanel = createInputPanel("Ingrese el título del libro:");
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Buscar Libro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String titulo = ((JTextField) inputPanel.getComponent(1)).getText();
            try {
                if (titulo != null && !titulo.trim().isEmpty()) {
                    LibroModel libro = libroController.buscarLibroPorTitulo(titulo);
                    JPanel messagePanel;
                    if (libro != null) {
                        messagePanel = createMessagePanel("Libro encontrado: " + libro.getTitulo() + " - " + libro.getAutor());
                    } else {
                        messagePanel = createMessagePanel("No se encontró ningún libro con ese título.");
                    }
                    JOptionPane.showMessageDialog(this, messagePanel, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                JPanel errorPanel = createMessagePanel("Error al buscar libro: " + e.getMessage());
                JOptionPane.showMessageDialog(this, errorPanel, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarLibrosPorAutor() {
        JPanel inputPanel = createInputPanel("Ingrese el autor:");
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Buscar Libros por Autor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String autor = ((JTextField) inputPanel.getComponent(1)).getText();
            try {
                if (autor != null && !autor.trim().isEmpty()) {
                    List<LibroModel> librosPorAutor = libroController.buscarLibrosPorAutor(autor);
                    JPanel messagePanel;
                    if (librosPorAutor.isEmpty()) {
                        messagePanel = createMessagePanel("No se encontraron libros de este autor.");
                    } else {
                        StringBuilder mensaje = new StringBuilder("Libros de " + autor + ":\n");
                        for (LibroModel libro : librosPorAutor) {
                            mensaje.append(libro.getTitulo()).append("\n ");
                        }
                        messagePanel = createMessagePanel(mensaje.toString());
                    }
                    JOptionPane.showMessageDialog(this, messagePanel, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                JPanel errorPanel = createMessagePanel("Error al buscar libros por autor: " + e.getMessage());
                JOptionPane.showMessageDialog(this, errorPanel, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarLibrosPorGenero() {
        JPanel inputPanel = createInputPanel("Ingrese el género:");
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Buscar Libros por Género", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String genero = ((JTextField) inputPanel.getComponent(1)).getText();
            try {
                if (genero != null && !genero.trim().isEmpty()) {
                    List<LibroModel> librosPorGenero = libroController.buscarLibrosPorGenero(genero);
                    JPanel messagePanel;
                    if (librosPorGenero.isEmpty()) {
                        messagePanel = createMessagePanel("No se encontraron libros para este género.");
                    } else {
                        StringBuilder mensaje = new StringBuilder("Libros del género " + genero + ":\n");
                        for (LibroModel libro : librosPorGenero) {
                            mensaje.append(libro.getTitulo()).append("\n ");
                        }
                        messagePanel = createMessagePanel(mensaje.toString());
                    }
                    JOptionPane.showMessageDialog(this, messagePanel, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                JPanel errorPanel = createMessagePanel("Error al buscar libros por género: " + e.getMessage());
                JOptionPane.showMessageDialog(this, errorPanel, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private JPanel createMenuAdministradorPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10)); // 9 filas para 9 botones
        panel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10)); // Espaciado alrededor del panel

        // Crear y agregar botones al panel
        JPanel btnRegistrarLibroPanel = createStyledButtonPanel("Registrar libro");
        JPanel btnVerInventarioPanel = createStyledButtonPanel("Ver inventario");
        JPanel btnActualizarLibroPanel = createStyledButtonPanel("Actualizar libro");
        JPanel btnBuscarPorTituloPanel = createStyledButtonPanel("Buscar libro por título");
        JPanel btnBuscarPorAutorPanel = createStyledButtonPanel("Buscar libro por autor");
        JPanel btnBuscarPorGeneroPanel = createStyledButtonPanel("Buscar libro por género");
        JPanel btnVerUsuariosPanel = createStyledButtonPanel("Ver usuarios");
        JPanel btnVerComprasPanel = createStyledButtonPanel("Ver todas las compras");

        JButton btnRegistrarLibro = (JButton) btnRegistrarLibroPanel.getComponent(0);
        JButton btnVerInventario = (JButton) btnVerInventarioPanel.getComponent(0);
        JButton btnActualizarLibro = (JButton) btnActualizarLibroPanel.getComponent(0);
        JButton btnBuscarPorTitulo = (JButton) btnBuscarPorTituloPanel.getComponent(0);
        JButton btnBuscarPorAutor = (JButton) btnBuscarPorAutorPanel.getComponent(0);
        JButton btnBuscarPorGenero = (JButton) btnBuscarPorGeneroPanel.getComponent(0);
        JButton btnVerUsuarios = (JButton) btnVerUsuariosPanel.getComponent(0);
        JButton btnVerCompras = (JButton) btnVerComprasPanel.getComponent(0);

        // Agregar ActionListeners a los botones
        btnRegistrarLibro.addActionListener(e -> registrarLibro());
        btnVerInventario.addActionListener(e -> verInventario());
        btnActualizarLibro.addActionListener(e -> actualizarLibro());
        btnBuscarPorTitulo.addActionListener(e -> buscarLibroPorTitulo());
        btnBuscarPorAutor.addActionListener(e -> buscarLibrosPorAutor());
        btnBuscarPorGenero.addActionListener(e -> buscarLibrosPorGenero());
        btnVerUsuarios.addActionListener(e -> verUsuarios());
        btnVerCompras.addActionListener(e -> verCompras());

        // Agregar botones al panel
        panel.add(btnRegistrarLibroPanel);
        panel.add(btnVerInventarioPanel);
        panel.add(btnActualizarLibroPanel);
        panel.add(btnBuscarPorTituloPanel);
        panel.add(btnBuscarPorAutorPanel);
        panel.add(btnBuscarPorGeneroPanel);
        panel.add(btnVerUsuariosPanel);
        panel.add(btnVerComprasPanel);

        return panel;
    }

    private void registrarLibro() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado alrededor de los componentes
        gbc.anchor = GridBagConstraints.WEST; // Alinear los componentes a la izquierda

        // Crear y agregar los campos de entrada
        JTextField tituloField = new JTextField(20);
        JTextField autorField = new JTextField(20);
        JTextField generoField = new JTextField(20);
        JTextField isbnField = new JTextField(20);
        JTextField precioField = new JTextField(20);
        JTextField categoriaField = new JTextField(20);
        JTextField estadoField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1;
        panel.add(tituloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Autor:"), gbc);

        gbc.gridx = 1;
        panel.add(autorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Género:"), gbc);

        gbc.gridx = 1;
        panel.add(generoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("ISBN:"), gbc);

        gbc.gridx = 1;
        panel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Precio:"), gbc);

        gbc.gridx = 1;
        panel.add(precioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Categoría:"), gbc);

        gbc.gridx = 1;
        panel.add(categoriaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1;
        panel.add(estadoField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar Libro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String titulo = tituloField.getText();
                String autor = autorField.getText();
                String genero = generoField.getText();
                int ISBN = Integer.parseInt(isbnField.getText());
                double precio = Double.parseDouble(precioField.getText());
                String categoria = categoriaField.getText();
                String estado = estadoField.getText();

                // Registrar libro en la base de datos
                libroController.agregarLibro(titulo, autor, genero, ISBN, (int) precio, categoria, estado);
                JOptionPane.showMessageDialog(this, "Libro registrado exitosamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados. Asegúrese de que ISBN y precio sean números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al registrar libro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void verInventario() {
        try {
            List<LibroModel> libros = libroController.consultarTodosLosLibros();
            if (libros.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El inventario está vacío.");
            } else {
                StringBuilder mensaje = new StringBuilder("Inventario de Libros:\n");
                for (LibroModel libro : libros) {
                    mensaje.append(libro.getTitulo()).append(" - ").append(libro.getAutor()).append("\n");
                }
                JOptionPane.showMessageDialog(this, mensaje.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener inventario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLibro() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Ingrese el título del libro a eliminar:");
            if (titulo != null && !titulo.trim().isEmpty()) {
                libroController.eliminarLibro(Integer.parseInt(titulo));
                JOptionPane.showMessageDialog(this, "Libro eliminado exitosamente.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar libro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarLibro() {
        JPanel panel = new JPanel(new GridLayout(8, 2));
        JTextField txtId = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtGenero = new JTextField();
        JTextField txtISBN = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtCategoria = new JTextField();
        JTextField txtEstado = new JTextField();

        panel.add(new JLabel("ID del libro:"));
        panel.add(txtId);
        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Autor:"));
        panel.add(txtAutor);
        panel.add(new JLabel("Género:"));
        panel.add(txtGenero);
        panel.add(new JLabel("ISBN:"));
        panel.add(txtISBN);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);
        panel.add(new JLabel("Estado:"));
        panel.add(txtEstado);

        int result = JOptionPane.showConfirmDialog(this, panel, "Actualizar Libro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                String genero = txtGenero.getText();
                int ISBN = Integer.parseInt(txtISBN.getText());
                int precio = Integer.parseInt(txtPrecio.getText());
                String categoria = txtCategoria.getText();
                String estado = txtEstado.getText();

                libroController.actualizarLibro(id, titulo, autor, genero, ISBN, precio, categoria, estado);
                JOptionPane.showMessageDialog(this, "Libro actualizado exitosamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados. Asegúrese de que el ID, ISBN y el precio sean números.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void verUsuarios() {
        String usuarioIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario:");
        if (usuarioIdStr != null && !usuarioIdStr.trim().isEmpty()) {
            try {
                int usuarioId = Integer.parseInt(usuarioIdStr.trim());
                UsuarioModel usuario = usuarioController.consultarUsuario(usuarioId);
                if (usuario != null) {
                    JPanel panel = new JPanel(new GridLayout(0, 2));
                    panel.add(new JLabel("ID:"));
                    panel.add(new JLabel(String.valueOf(usuario.getUsuarioId())));
                    panel.add(new JLabel("Nombre:"));
                    panel.add(new JLabel(usuario.getNombre()));
                    panel.add(new JLabel("Apellido:"));
                    panel.add(new JLabel(usuario.getApellido()));
                    panel.add(new JLabel("Correo:"));
                    panel.add(new JLabel(usuario.getCorreo()));
                    panel.add(new JLabel("Rol:"));
                    panel.add(new JLabel(usuario.getRol()));
                    panel.add(new JLabel("Género:"));
                    panel.add(new JLabel(usuario.getGenero()));
                    panel.add(new JLabel("Fecha de Nacimiento:"));
                    panel.add(new JLabel(usuario.getFechaNacimiento().toString()));
                    panel.add(new JLabel("Edad:"));
                    panel.add(new JLabel(String.valueOf(usuario.getEdad())));
                    panel.add(new JLabel("ID Física:"));
                    panel.add(new JLabel(String.valueOf(usuario.getIdFisica())));

                    JOptionPane.showMessageDialog(this, panel, "Información del Usuario", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario no encontrado");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID de usuario inválido", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al consultar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID de usuario no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verCompras() {
        try {
            List<CompraModel> todasLasCompras = compraController.consultarTodasLasCompras();
            if (todasLasCompras.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay compras registradas.");
            } else {
                StringBuilder sb = new StringBuilder("Compras registradas:\n");
                for (CompraModel compra : todasLasCompras) {
                    sb.append("ID: ").append(compra.getId())
                            .append(", Número de Compra: ").append(compra.getNumeroDeCompra())
                            .append(", Usuario de Venta: ").append(compra.getUsuarioVenta())
                            .append(", Fecha de Venta: ").append(compra.getFechaDeVenta())
                            .append(", Usuario ID: ").append(compra.getUsuarioId())
                            .append(", Libro ID: ").append(compra.getLibroId())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar todas las compras: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createMenuVendedorPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10)); // 5 filas, 1 columna, con separación de 10px

        // Botón para registrar compra
        JPanel registrarCompraButtonPanel = createStyledButtonPanel("Registrar compra");
        JPanel buscarPorTituloButtonPanel = createStyledButtonPanel("Buscar libro por título");
        JPanel buscarPorAutorButtonPanel = createStyledButtonPanel("Buscar libros por autor");
        JPanel buscarPorGeneroButtonPanel = createStyledButtonPanel("Buscar libros por género");
        JPanel verComprasButtonPanel = createStyledButtonPanel("Ver compras realizadas");

        JButton registrarCompraButton = (JButton) registrarCompraButtonPanel.getComponent(0);
        JButton buscarPorTituloButton = (JButton) buscarPorTituloButtonPanel.getComponent(0);
        JButton buscarPorAutorButton = (JButton) buscarPorAutorButtonPanel.getComponent(0);
        JButton buscarPorGeneroButton = (JButton) buscarPorGeneroButtonPanel.getComponent(0);
        JButton verComprasButton = (JButton) verComprasButtonPanel.getComponent(0);

        registrarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCompra();
            }
        });

        buscarPorTituloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLibroPorTitulo();
            }
        });

        buscarPorAutorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLibrosPorAutor();
            }
        });

        buscarPorGeneroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLibrosPorGenero();
            }
        });

        verComprasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verCompras();
            }
        });

        // Agregar botones al panel
        panel.add(registrarCompraButtonPanel);
        panel.add(buscarPorTituloButtonPanel);
        panel.add(buscarPorAutorButtonPanel);
        panel.add(buscarPorGeneroButtonPanel);
        panel.add(verComprasButtonPanel);

        return panel;
    }

    private void registrarCompra() {
        // Crear un panel con GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado alrededor de los componentes
        gbc.anchor = GridBagConstraints.WEST; // Alinear los componentes a la izquierda

        // Crear y agregar los campos de entrada
        JTextField numeroDeCompraField = new JTextField(20);
        JTextField usuarioVentaField = new JTextField(20);
        JTextField fechaDeVentaField = new JTextField(20);
        JTextField usuarioIdField = new JTextField(20);
        JTextField libroIdField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Número de Compra:"), gbc);

        gbc.gridx = 1;
        panel.add(numeroDeCompraField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Usuario de Venta:"), gbc);

        gbc.gridx = 1;
        panel.add(usuarioVentaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Fecha de Venta (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        panel.add(fechaDeVentaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("ID de Usuario:"), gbc);

        gbc.gridx = 1;
        panel.add(usuarioIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("ID de Libro:"), gbc);

        gbc.gridx = 1;
        panel.add(libroIdField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Ingrese los detalles de la compra", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int numeroDeCompra = Integer.parseInt(numeroDeCompraField.getText());
                String usuarioVenta = usuarioVentaField.getText();
                String fechaDeVenta = fechaDeVentaField.getText();
                int usuarioId = Integer.parseInt(usuarioIdField.getText());
                int libroId = Integer.parseInt(libroIdField.getText());

                compraController.agregarCompra(numeroDeCompra, usuarioVenta, fechaDeVenta, usuarioId, libroId);
                JOptionPane.showMessageDialog(this, "Compra registrada correctamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese datos válidos", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar la compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void verComprasRealizadas() {
        // Implementar método para ver compras realizadas
    }

    private void showRegisterDialog() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado alrededor de los componentes

        // Crear y agregar los campos de entrada
        JTextField nombreField = new JTextField(20);
        JTextField apellidoField = new JTextField(20);
        JTextField correoField = new JTextField(20);
        JPasswordField contrasenaField = new JPasswordField(20);
        JTextField rolField = new JTextField(20);
        JTextField generoField = new JTextField(20);
        JTextField fechaNacimientoField = new JTextField(20);
        JTextField edadField = new JTextField(20);
        JTextField IdFisicaField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        panel.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        panel.add(apellidoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Correo:"), gbc);

        gbc.gridx = 1;
        panel.add(correoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        panel.add(contrasenaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Rol:"), gbc);

        gbc.gridx = 1;
        panel.add(rolField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Género:"), gbc);

        gbc.gridx = 1;
        panel.add(generoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Fecha de Nacimiento:"), gbc);

        gbc.gridx = 1;
        panel.add(fechaNacimientoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Edad:"), gbc);

        gbc.gridx = 1;
        panel.add(edadField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Id Física:"), gbc);

        gbc.gridx = 1;
        panel.add(IdFisicaField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registro de Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();
                String correo = correoField.getText();
                String contrasena = new String(contrasenaField.getPassword());
                String rol = rolField.getText();
                String genero = generoField.getText();
                String fechaNacimiento = fechaNacimientoField.getText();
                int edad = Integer.parseInt(edadField.getText());
                int idFisica = Integer.parseInt(IdFisicaField.getText());

                // Registrar usuario en la base de datos
                usuarioController.agregarUsuario(nombre, apellido, correo, contrasena, rol, genero, LocalDate.parse(fechaNacimiento), edad, idFisica);
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados. Asegúrese de que la edad sea un número.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void login(String email, String password) {
        usuarioActual = usuarioController.iniciarSesion(email, password);
        if (usuarioActual != null) {
            switch (usuarioActual.getRol()) {
                case "Administrador":
                    cardLayout.show(mainPanel, "menuAdministradorPanel");
                    break;
                case "Vendedor":
                    cardLayout.show(mainPanel, "menuVendedorPanel");
                    break;
                case "Comprador":
                    cardLayout.show(mainPanel, "menuCompradorPanel");
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Rol de usuario desconocido.");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Inicio de sesión fallido.");
        }
    }
    private JPanel createStyledButtonPanel(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Crear un panel para envolver el botón y agregar margen
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Margen alrededor del botón
        panel.add(button, BorderLayout.CENTER);

        return panel;
    }
    private JDialog createStyledDialog(String title, String message, int messageType) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout());

        JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messagePanel.add(messageLabel);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);

        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        return dialog;
    }

    public static void main(String[] args) {
        // Inicializar los controladores aquí
        LibroController libroController = new LibroController(new ConsoleView());
        CompraController compraController = new CompraController(new ConsoleView());
        UsuarioController usuarioController = new UsuarioController(new ConsoleView());

        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI(libroController, compraController, usuarioController);
            mainGUI.setVisible(true);
        });
    }
}
