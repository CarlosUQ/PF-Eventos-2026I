package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una zona dentro de un recinto.
 *
 * Una zona tiene capacidad, precio base, tipo y asientos.
 */
public class Zona {

    private String idZona;

    private String nombre;

    private int capacidad;

    private double precioBase;

    private TipoZona tipoZona;

    private List<Asiento> asientos;

    /**
     * Crea una zona con sus datos principales.
     *
     * @param idZona identificador de la zona
     * @param nombre nombre de la zona
     * @param capacidad capacidad maxima
     * @param precioBase precio base de la zona
     * @param tipoZona tipo de zona
     */
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


    /**
     * Agrega un asiento a la zona.
     *
     * @param asiento asiento que se desea agregar
     */
    public void agregarAsiento(Asiento asiento) {
        asientos.add(asiento);
    }

    /**
     * Elimina un asiento por su identificador.
     *
     * @param idAsiento identificador del asiento
     * @return true si el asiento fue eliminado
     */
    public boolean eliminarAsiento(String idAsiento) {
        return asientos.removeIf(asiento -> asiento.getIdAsiento().equals(idAsiento));
    }

    /**
     * Busca un asiento por su identificador.
     *
     * @param idAsiento identificador del asiento
     * @return asiento encontrado o null
     */
    public Asiento obtenerAsientoPorId(String idAsiento) {

        for (Asiento asiento : asientos) {

            if (asiento.getIdAsiento().equals(idAsiento)) {
                return asiento;
            }
        }

        return null;
    }

    /**
     * Indica si existe un asiento en la zona.
     *
     * @param idAsiento identificador del asiento
     * @return true si existe
     */
    public boolean existeAsiento(String idAsiento) {

        return obtenerAsientoPorId(idAsiento) != null;
    }

    /**
     * Consulta los asientos disponibles.
     *
     * @return lista de asientos disponibles
     */
    public List<Asiento> consultarAsientosDisponibles() {

        List<Asiento> disponibles = new ArrayList<>();

        for (Asiento asiento : asientos) {

            if (asiento.estaDisponible()) {
                disponibles.add(asiento);
            }
        }

        return disponibles;
    }

    /**
     * Cuenta los asientos disponibles.
     *
     * @return cantidad de asientos disponibles
     */
    public int obtenerCantidadDisponible() {

        int disponibles = 0;

        for (Asiento asiento : asientos) {

            if (asiento.estaDisponible()) {
                disponibles++;
            }
        }

        return disponibles;
    }

    /**
     * Indica si la zona no tiene asientos disponibles.
     *
     * @return true si la zona esta llena
     */
    public boolean estaLlena() {
        return obtenerCantidadDisponible() == 0;
    }

    /**
     * Cuenta los asientos ocupados.
     *
     * @return cantidad de asientos ocupados
     */
    public int consultarOcupacion() {

        int ocupados = 0;

        for (Asiento asiento : asientos) {

            if (asiento.getEstadoAsiento() != EstadoAsiento.DISPONIBLE) {
                ocupados++;
            }
        }

        return ocupados;
    }


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
