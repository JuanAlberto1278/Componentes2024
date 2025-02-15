package Model;

import java.time.LocalDate;
import java.time.Period;

public class UsuarioModel {
  private int UsuarioId;
  private String nombre;
  private String apellido;
  private String correo;
  private String contrasena;
  private String rol;
  private String genero;
  private LocalDate fechaNacimiento;
  private int edad;
  private int idFisica;

  // Constructor
  public UsuarioModel(int UsuarioId, String nombre, String apellido, String correo, String contrasena,
                      String rol, String genero, LocalDate fechaNacimiento, int idFisica, int fisica) {
    this.UsuarioId = UsuarioId;
    this.nombre = nombre;
    this.apellido = apellido;
    this.correo = correo;
    this.contrasena = contrasena;
    this.rol = rol;
    this.genero = genero;
    this.fechaNacimiento = fechaNacimiento;
    this.idFisica = idFisica;
    this.edad = calcularEdad(fechaNacimiento); // Calcula edad al crear el objeto
  }
  public UsuarioModel(String nombre, String apellido, String correo, String contrasena, String rol, String genero, LocalDate fechaNacimiento, int edad, int idFisica) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.correo = correo;
    this.contrasena = contrasena;
    this.rol = rol;
    this.genero = genero;
    this.fechaNacimiento = fechaNacimiento;
    this.edad = edad;
    this.idFisica = idFisica;
  }

  public UsuarioModel(String nombre, String apellido, String correo, String contrasena, String genero, LocalDate fechaNacimiento, int idFisica) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.correo = correo;
    this.contrasena = contrasena;
    this.genero = genero;
    this.fechaNacimiento = fechaNacimiento;
    this.idFisica = idFisica;
  }

  // Getters y Setters
  public int getUsuarioId() {
    return UsuarioId;
  }

  public void setUsuarioId(int usuarioId) {
    this.UsuarioId = usuarioId;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
    this.edad = calcularEdad(fechaNacimiento); // Recalcula la edad si cambia la fecha de nacimiento
  }

  public int getEdad() {
    return edad;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public int getIdFisica() {
    return idFisica;
  }

  public void setIdFisica(int idFisica) {
    this.idFisica = idFisica;
  }

  // Método para calcular la edad a partir de la fecha de nacimiento
  private int calcularEdad(LocalDate fechaNacimiento) {
    if (fechaNacimiento == null) {
      return 0;
    }
    return Period.between(fechaNacimiento, LocalDate.now()).getYears();
  }

  @Override
  public String toString() {
    return "UsuarioModel{" +
        "id=" + UsuarioId +
        ", nombre='" + nombre + '\'' +
        ", apellido='" + apellido + '\'' +
        ", correo='" + correo + '\'' +
        ", contrasena='" + contrasena + '\'' +
        ", rol='" + rol + '\'' +
        ", genero='" + genero + '\'' +
        ", fechaNacimiento=" + fechaNacimiento +
        ", edad=" + edad +
        ", idFisica=" + idFisica +
        '}';
  }

}
