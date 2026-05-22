package model;

public class Asiento {

    private String idAsiento;

    private String fila;

    private int numero;

    private EstadoAsiento estadoAsiento;

    public Asiento(String idAsiento,
                   String fila,
                   int numero,
                   EstadoAsiento estadoAsiento) {

        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estadoAsiento = estadoAsiento;
    }


    public void cambiarEstado(EstadoAsiento nuevoEstado) {
        this.estadoAsiento = nuevoEstado;
    }

    public boolean estaDisponible() {
        return estadoAsiento == EstadoAsiento.DISPONIBLE;
    }

    public void reservar() {

        if (estadoAsiento == EstadoAsiento.DISPONIBLE) {
            estadoAsiento = EstadoAsiento.RESERVADO;
        }
    }

    public void vender() {

        if (estadoAsiento == EstadoAsiento.DISPONIBLE
                || estadoAsiento == EstadoAsiento.RESERVADO) {

            estadoAsiento = EstadoAsiento.VENDIDO;
        }
    }

    public void bloquear() {
        estadoAsiento = EstadoAsiento.BLOQUEADO;
    }

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