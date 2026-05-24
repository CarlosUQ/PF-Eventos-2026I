package model;

/**
 * Clase base para decorar servicios adicionales.
 *
 * Permite agregar comportamiento o precio a un servicio existente.
 */
public abstract class ServicioDecorator implements ServicioAdicional {

    protected ServicioAdicional servicio;

    /**
     * Crea un decorador sobre un servicio existente.
     *
     * @param servicio servicio que se va a decorar
     */
    public ServicioDecorator(ServicioAdicional servicio) {
        this.servicio = servicio;
    }

    @Override
    public String getDescripcion() {
        return servicio.getDescripcion();
    }

    @Override
    public double getPrecio() {
        return servicio.getPrecio();
    }

    @Override
    public String getIdServicio() {
        return servicio.getIdServicio();
    }
}
