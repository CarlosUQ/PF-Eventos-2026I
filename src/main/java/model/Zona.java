package model;

import java.util.ArrayList;
import java.util.List;

public class Zona {

    private String idZona;

    private String nombre;

    private int capacidad;

    private double precioBase;

    private TipoZona tipoZona;

    private List<Asiento> asientos;

    public Zona(String idZona,
                String nombre,
                int capacidad,
                double precioBase,
                TipoZona tipoZona) {

        this.idZona = idZona;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.tipoZona = tipoZona;

        this.asientos = new ArrayList<>();
    }

    // =========================
    // MÉTODOS
    // =========================

    public void agregarAsiento(Asiento asiento) {
        asientos.add(asiento);
    }

    public boolean eliminarAsiento(String idAsiento) {
        return asientos.removeIf(asiento -> asiento.getIdAsiento().equals(idAsiento));
    }

    public Asiento obtenerAsientoPorId(String idAsiento) {

        for (Asiento asiento : asientos) {

            if (asiento.getIdAsiento().equals(idAsiento)) {
                return asiento;
            }
        }

        return null;
    }

    public boolean existeAsiento(String idAsiento) {

        return obtenerAsientoPorId(idAsiento) != null;
    }

    public List<Asiento> consultarAsientosDisponibles() {

        List<Asiento> disponibles = new ArrayList<>();

        for (Asiento asiento : asientos) {

            if (asiento.estaDisponible()) {
                disponibles.add(asiento);
            }
        }

        return disponibles;
    }

    public int obtenerCantidadDisponible() {

        int disponibles = 0;

        for (Asiento asiento : asientos) {

            if (asiento.estaDisponible()) {
                disponibles++;
            }
        }

        return disponibles;
    }

    public boolean estaLlena() {
        return obtenerCantidadDisponible() == 0;
    }

    public int consultarOcupacion() {

        int ocupados = 0;

        for (Asiento asiento : asientos) {

            if (asiento.getEstadoAsiento() != EstadoAsiento.DISPONIBLE) {
                ocupados++;
            }
        }

        return ocupados;
    }

    // =========================
    // GETTERS Y SETTERS
    // =========================

    public String getIdZona() {
        return idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public TipoZona getTipoZona() {
        return tipoZona;
    }

    public void setTipoZona(TipoZona tipoZona) {
        this.tipoZona = tipoZona;
    }

    public List<Asiento> getAsientos() {
        return new ArrayList<>(asientos);
    }

    @Override
    public String toString() {
        return "Zona{" +
                "idZona='" + idZona + '\'' +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", precioBase=" + precioBase +
                ", tipoZona=" + tipoZona +
                '}';
    }
}
