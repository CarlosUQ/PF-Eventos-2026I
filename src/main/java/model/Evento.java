package model;

import java.time.LocalDateTime;
import java.util.List;

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

    public boolean estaDisponible() {
        return estadoEvento == EstadoEvento.PUBLICADO;
    }

    public boolean estaFinalizado() {
        return estadoEvento == EstadoEvento.FINALIZADO;
    }

    public boolean estaCancelado() {
        return estadoEvento == EstadoEvento.CANCELADO;
    }

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

