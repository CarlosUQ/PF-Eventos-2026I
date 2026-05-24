package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa un evento disponible en el sistema.
 *
 * Un evento pertenece a un recinto y tiene informacion como
 * nombre, categoria, ciudad, fecha y estado.
 */
public class Evento {

    private String idEvento;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String ciudad;
    private LocalDateTime fechaHora;
    private EstadoEvento estadoEvento;
    private String politicaCancelacion;
    private String politicaReembolso;
    private Recinto recinto;

    /**
     * Crea un evento con sus datos principales.
     *
     * @param idEvento identificador del evento
     * @param nombre nombre del evento
     * @param categoria categoria del evento
     * @param descripcion descripcion del evento
     * @param ciudad ciudad donde se realiza
     * @param fechaHora fecha y hora del evento
     * @param estadoEvento estado inicial del evento
     * @param recinto recinto donde se realiza
     */
    public Evento(String idEvento,
                  String nombre,
                  String categoria,
                  String descripcion,
                  String ciudad,
                  LocalDateTime fechaHora,
                  EstadoEvento estadoEvento,
                  Recinto recinto) {

        this.idEvento = idEvento;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fechaHora = fechaHora;
        this.estadoEvento = estadoEvento;
        this.politicaCancelacion = "";
        this.politicaReembolso = "";
        this.recinto = recinto;
    }

    /**
     * Indica si el evento esta publicado.
     *
     * @return true si el evento esta disponible
     */
    public boolean estaDisponible() {
        return estadoEvento == EstadoEvento.PUBLICADO;
    }

    /**
     * Indica si el evento ya finalizo.
     *
     * @return true si el evento esta finalizado
     */
    public boolean estaFinalizado() {
        return estadoEvento == EstadoEvento.FINALIZADO;
    }

    /**
     * Indica si el evento fue cancelado.
     *
     * @return true si el evento esta cancelado
     */
    public boolean estaCancelado() {
        return estadoEvento == EstadoEvento.CANCELADO;
    }

    /**
     * Obtiene las zonas del recinto del evento.
     *
     * @return lista de zonas del recinto
     */
    public List<Zona> obtenerZonas() {
        return recinto.getZonas();
    }

    // GETTERS / SETTERS
    public String getIdEvento() { return idEvento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public EstadoEvento getEstadoEvento() { return estadoEvento; }
    public void setEstadoEvento(EstadoEvento estadoEvento) { this.estadoEvento = estadoEvento; }

    public String getPoliticaCancelacion() { return politicaCancelacion; }
    public void setPoliticaCancelacion(String politicaCancelacion) { this.politicaCancelacion = politicaCancelacion; }

    public String getPoliticaReembolso() { return politicaReembolso; }
    public void setPoliticaReembolso(String politicaReembolso) { this.politicaReembolso = politicaReembolso; }

    public Recinto getRecinto() { return recinto; }
    public void setRecinto(Recinto recinto) { this.recinto = recinto; }
}
