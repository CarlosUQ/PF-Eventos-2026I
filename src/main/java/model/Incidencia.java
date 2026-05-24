package model;

import java.time.LocalDateTime;

public class Incidencia {

    private String idIncidencia;

    private String tipo;

    private String descripcion;

    private LocalDateTime fecha;

    private String entidadAfectada;

    public Incidencia(String idIncidencia,
                      String tipo,
                      String descripcion,
                      LocalDateTime fecha,
                      String entidadAfectada) {

        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.entidadAfectada = entidadAfectada;
    }

    public boolean esDeTipo(String tipo) {
        return this.tipo.equalsIgnoreCase(tipo);
    }

    public boolean afectaEntidad(String entidad) {
        return this.entidadAfectada.equalsIgnoreCase(entidad);
    }


    public String getIdIncidencia() {
        return idIncidencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEntidadAfectada() {
        return entidadAfectada;
    }

    public void setEntidadAfectada(String entidadAfectada) {
        this.entidadAfectada = entidadAfectada;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "idIncidencia='" + idIncidencia + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fecha=" + fecha +
                ", entidadAfectada='" + entidadAfectada + '\'' +
                '}';
    }
}