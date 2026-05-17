package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario implements IObservador {

    protected String idUsuario;
    protected String nombreCompleto;
    protected String correoElectronico;
    protected String numeroTelefono;
    protected String contrasena;

    protected List<String> metodosPagoRegistrados;
    protected List<Compra> compras;

    public Usuario(String idUsuario,
                   String nombreCompleto,
                   String correoElectronico,
                   String numeroTelefono,
                   String contrasena) {

        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono = numeroTelefono;
        this.contrasena = contrasena;

        this.metodosPagoRegistrados = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    // =========================
    // MÉTODOS DE PAGO
    // =========================

    public void agregarMetodoPago(String metodoPago) {
        if (!metodosPagoRegistrados.contains(metodoPago)) {
            metodosPagoRegistrados.add(metodoPago);
        }
    }

    public boolean eliminarMetodoPago(String metodoPago) {
        return metodosPagoRegistrados.remove(metodoPago);
    }

    // =========================
    // OBSERVER
    // =========================

    @Override
    public void actualizar(String mensaje) {
        System.out.println("Notificación para " + nombreCompleto + ": " + mensaje);
    }

    // =========================
    // COMPRAS
    // =========================

    public void agregarCompra(Compra compra) {
        if (compra != null && !compras.contains(compra)) {
            compras.add(compra);
        }
    }

    public Compra obtenerCompraPorId(String idCompra) {

        for (Compra compra : compras) {
            if (compra.getIdCompra().equals(idCompra)) {
                return compra;
            }
        }

        return null;
    }

    // =========================
    // PERFIL
    // =========================

    public void actualizarPerfil(String nombre,
                                 String correo,
                                 String telefono) {

        this.nombreCompleto = nombre;
        this.correoElectronico = correo;
        this.numeroTelefono = telefono;
    }

    // =========================
    // GETTERS
    // =========================

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public List<String> getMetodosPagoRegistrados() {
        return new ArrayList<>(metodosPagoRegistrados);
    }

    public List<Compra> getCompras() {
        return new ArrayList<>(compras);
    }

    // =========================
    // TO STRING
    // =========================

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                '}';
    }
}