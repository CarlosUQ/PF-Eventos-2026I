package model;

/**
 * Define un servicio adicional que puede agregarse a una compra.
 */
public interface ServicioAdicional {

    /**
     * Obtiene el identificador del servicio.
     *
     * @return identificador del servicio
     */
    String getIdServicio();

    /**
     * Obtiene la descripcion del servicio.
     *
     * @return descripcion del servicio
     */
    String getDescripcion();

    /**
     * Obtiene el precio del servicio.
     *
     * @return precio del servicio
     */
    double getPrecio();
}
