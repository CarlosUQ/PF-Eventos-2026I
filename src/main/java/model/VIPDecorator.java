package model;

/**
 * Decorador que agrega beneficio VIP a un servicio.
 */
public class VIPDecorator extends ServicioDecorator {

    /**
     * Crea el decorador VIP.
     *
     * @param servicio servicio base
     */
    public VIPDecorator(ServicioAdicional servicio) {
        super(servicio);
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + VIP";
    }

    @Override
    public double getPrecio() {
        return super.getPrecio() + 50000;
    }

}
