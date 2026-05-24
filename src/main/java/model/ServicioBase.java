package model;

/**
 * Servicio adicional simple con descripcion y precio.
 */
public class ServicioBase implements ServicioAdicional {

    private String idServicio;
    private String nombreServicio;
    private double precio;

    /**
     * Crea un servicio base.
     *
     * @param idServicio identificador del servicio
     * @param nombreServicio nombre o descripcion del servicio
     * @param precio precio del servicio
     */
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
