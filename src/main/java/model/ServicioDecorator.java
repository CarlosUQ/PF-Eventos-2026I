package model;

public abstract class ServicioDecorator implements ServicioAdicional {

    protected ServicioAdicional servicio;

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