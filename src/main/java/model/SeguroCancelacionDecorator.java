package model;

/**
 * Decorador que agrega seguro de cancelacion a un servicio.
 */
public class SeguroCancelacionDecorator extends ServicioDecorator {

    /**
     * Crea el decorador de seguro.
     *
     * @param servicio servicio base
     */
    public SeguroCancelacionDecorator(ServicioAdicional servicio) {
        super(servicio);
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Seguro";
    }

    @Override
    public double getPrecio() {
        return super.getPrecio() + 20000;
    }

}
