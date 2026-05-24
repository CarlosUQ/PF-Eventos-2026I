package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un recinto donde se realizan eventos.
 *
 * Un recinto contiene una o varias zonas.
 */
public class Recinto {

    private String idRecinto;

    private String nombre;

    private String direccion;

    private String ciudad;

    private List<Zona> zonas;

    /**
     * Crea un recinto con sus datos principales.
     *
     * @param idRecinto identificador del recinto
     * @param nombre nombre del recinto
     * @param direccion direccion del recinto
     * @param ciudad ciudad del recinto
     */
    public Recinto(String idRecinto,
                   String nombre,
                   String direccion,
                   String ciudad) {

        this.idRecinto = idRecinto;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;

        this.zonas = new ArrayList<>();
    }


    /**
     * Agrega una zona al recinto.
     *
     * @param zona zona que se desea agregar
     */
    public void agregarZona(Zona zona) {
        zonas.add(zona);
    }

    /**
     * Elimina una zona por su identificador.
     *
     * @param idZona identificador de la zona
     * @return true si la zona fue eliminada
     */
    public boolean eliminarZona(String idZona) {
        return zonas.removeIf(zona -> zona.getIdZona().equals(idZona));
    }

    /**
     * Busca una zona por su identificador.
     *
     * @param idZona identificador de la zona
     * @return zona encontrada o null
     */
    public Zona obtenerZonaPorId(String idZona) {

        for (Zona zona : zonas) {

            if (zona.getIdZona().equals(idZona)) {
                return zona;
            }
        }

        return null;
    }

    /**
     * Indica si existe una zona.
     *
     * @param idZona identificador de la zona
     * @return true si existe
     */
    public boolean existeZona(String idZona) {

        return obtenerZonaPorId(idZona) != null;
    }

    /**
     * Suma la capacidad de todas las zonas.
     *
     * @return capacidad total del recinto
     */
    public int obtenerCapacidadTotal() {

        int capacidadTotal = 0;

        for (Zona zona : zonas) {
            capacidadTotal += zona.getCapacidad();
        }

        return capacidadTotal;
    }

    /**
     * Obtiene la cantidad de zonas del recinto.
     *
     * @return cantidad de zonas
     */
    public int obtenerCantidadZonas() {
        return zonas.size();
    }


    public String getIdRecinto() {
        return idRecinto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Zona> getZonas() {
        return new ArrayList<>(zonas);
    }

    @Override
    public String toString() {
        return "Recinto{" +
                "idRecinto='" + idRecinto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
