package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un usuario base del sistema.
 *
 * Guarda datos personales, metodos de pago y compras realizadas.
 */
public abstract class Usuario implements IObservador {

    protected String idUsuario;
    protected String nombreCompleto;
    protected String correoElectronico;
    protected String numeroTelefono;
    protected String contrasena;

    protected List<String> metodosPagoRegistrados;
    protected List<Compra> compras;

    /**
     * Crea un usuario con sus datos personales.
     *
     * @param idUsuario identificador del usuario
     * @param nombreCompleto nombre completo del usuario
     * @param correoElectronico correo electronico del usuario
     * @param numeroTelefono numero de telefono
     * @param contrasena contrasena del usuario
     */
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


    /**
     * Agrega un metodo de pago si no esta repetido.
     *
     * @param metodoPago metodo de pago que se desea guardar
     */
    public void agregarMetodoPago(String metodoPago) {
        if (!metodosPagoRegistrados.contains(metodoPago)) {
            metodosPagoRegistrados.add(metodoPago);
        }
    }

    /**
     * Elimina un metodo de pago.
     *
     * @param metodoPago metodo de pago que se desea eliminar
     * @return true si el metodo fue eliminado
     */
    public boolean eliminarMetodoPago(String metodoPago) {
        return metodosPagoRegistrados.remove(metodoPago);
    }


    /**
     * Recibe una notificacion.
     *
     * @param mensaje mensaje recibido
     */
    @Override
    public void actualizar(String mensaje) {
        System.out.println("Notificación para " + nombreCompleto + ": " + mensaje);
    }


    /**
     * Agrega una compra si no esta repetida.
     *
     * @param compra compra que se desea guardar
     */
    public void agregarCompra(Compra compra) {
        if (compra != null && !compras.contains(compra)) {
            compras.add(compra);
        }
    }

    /**
     * Busca una compra por su identificador.
     *
     * @param idCompra identificador de la compra
     * @return compra encontrada o null
     */
    public Compra obtenerCompraPorId(String idCompra) {

        for (Compra compra : compras) {
            if (compra.getIdCompra().equals(idCompra)) {
                return compra;
            }
        }

        return null;
    }


    /**
     * Actualiza los datos principales del perfil.
     *
     * @param nombre nuevo nombre
     * @param correo nuevo correo
     * @param telefono nuevo telefono
     */
    public void actualizarPerfil(String nombre,
                                 String correo,
                                 String telefono) {

        this.nombreCompleto = nombre;
        this.correoElectronico = correo;
        this.numeroTelefono = telefono;
    }


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


    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                '}';
    }
}
