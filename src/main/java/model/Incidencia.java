package model;

import java.time.LocalDateTime;

/**
 * Representa una incidencia registrada en el sistema.
 */
public class Incidencia {

    private String idIncidencia;

    private String tipo;

    private String descripcion;

    private LocalDateTime fecha;

    private String entidadAfectada;

    /**
     * Crea una incidencia.
     *
     * @param idIncidencia identificador de la incidencia
     * @param tipo tipo de incidencia
     * @param descripcion descripcion de lo ocurrido
     * @param fecha fecha de registro
     * @param entidadAfectada entidad afectada por la incidencia
     */
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

    /**
     * Indica si la incidencia coincide con un tipo.
     *
     * @param tipo tipo que se desea comparar
     * @return true si coincide
     */
    public boolean esDeTipo(String tipo) {
        return this.tipo.equalsIgnoreCase(tipo);
    }

    /**
     * Indica si la incidencia afecta a una entidad.
     *
     * @param entidad entidad que se desea comparar
     * @return true si coincide
     */
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
