package model;

public class VIPDecorator extends ServicioDecorator {

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