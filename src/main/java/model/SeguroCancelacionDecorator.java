package model;

public class SeguroCancelacionDecorator extends ServicioDecorator {

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