package model;

public class ServicioBase implements ServicioAdicional {

    private String idServicio;
    private String nombreServicio;
    private double precio;

    public ServicioBase(String idServicio, String nombreServicio, double precio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.precio = precio;
    }

    @Override
    public String getDescripcion() {
        return nombreServicio;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    @Override
    public String getIdServicio() {
        return idServicio;
    }
}