package model;

/**
 * Representa un asiento dentro de una zona.
 *
 * Un asiento puede estar disponible, reservado, vendido o bloqueado.
 */
public class Asiento {

    private String idAsiento;

    private String fila;

    private int numero;

    private EstadoAsiento estadoAsiento;

    /**
     * Crea un asiento con su ubicacion y estado inicial.
     *
     * @param idAsiento identificador del asiento
     * @param fila fila del asiento
     * @param numero numero del asiento
     * @param estadoAsiento estado inicial del asiento
     */
    public Asiento(String idAsiento,
                   String fila,
                   int numero,
                   EstadoAsiento estadoAsiento) {

        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estadoAsiento = estadoAsiento;
    }


    /**
     * Cambia el estado del asiento.
     *
     * @param nuevoEstado nuevo estado del asiento
     */
    public void cambiarEstado(EstadoAsiento nuevoEstado) {
        this.estadoAsiento = nuevoEstado;
    }

    /**
     * Indica si el asiento esta disponible.
     *
     * @return true si el asiento esta disponible
     */
    public boolean estaDisponible() {
        return estadoAsiento == EstadoAsiento.DISPONIBLE;
    }

    /**
     * Reserva el asiento si esta disponible.
     */
    public void reservar() {

        if (estadoAsiento == EstadoAsiento.DISPONIBLE) {
            estadoAsiento = EstadoAsiento.RESERVADO;
        }
    }

    /**
     * Marca el asiento como vendido si esta disponible o reservado.
     */
    public void vender() {

        if (estadoAsiento == EstadoAsiento.DISPONIBLE
                || estadoAsiento == EstadoAsiento.RESERVADO) {

            estadoAsiento = EstadoAsiento.VENDIDO;
        }
    }

    /**
     * Bloquea el asiento.
     */
    public void bloquear() {
        estadoAsiento = EstadoAsiento.BLOQUEADO;
    }

    /**
     * Libera el asiento y lo deja disponible.
     */
    public void liberar() {
        estadoAsiento = EstadoAsiento.DISPONIBLE;
    }


    public String getIdAsiento() {
        return idAsiento;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstadoAsiento() {
        return estadoAsiento;
    }

    public void setEstadoAsiento(EstadoAsiento estadoAsiento) {
        this.estadoAsiento = estadoAsiento;
    }

    @Override
    public String toString() {
        return "Asiento{" +
                "idAsiento='" + idAsiento + '\'' +
                ", fila='" + fila + '\'' +
                ", numero=" + numero +
                ", estadoAsiento=" + estadoAsiento +
                '}';
    }
}
