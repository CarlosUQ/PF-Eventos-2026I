package model;

import java.util.ArrayList;
import java.util.List;

public class Recinto {

    private String idRecinto;

    private String nombre;

    private String direccion;

    private String ciudad;

    private List<Zona> zonas;

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


    public void agregarZona(Zona zona) {
        zonas.add(zona);
    }

    public boolean eliminarZona(String idZona) {
        return zonas.removeIf(zona -> zona.getIdZona().equals(idZona));
    }

    public Zona obtenerZonaPorId(String idZona) {

        for (Zona zona : zonas) {

            if (zona.getIdZona().equals(idZona)) {
                return zona;
            }
        }

        return null;
    }

    public boolean existeZona(String idZona) {

        return obtenerZonaPorId(idZona) != null;
    }

    public int obtenerCapacidadTotal() {

        int capacidadTotal = 0;

        for (Zona zona : zonas) {
            capacidadTotal += zona.getCapacidad();
        }

        return capacidadTotal;
    }

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
