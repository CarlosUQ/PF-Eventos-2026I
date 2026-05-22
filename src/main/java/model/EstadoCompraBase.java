package model;

public abstract class EstadoCompraBase implements EstadoCompraState {

    protected void operacionNoPermitida() {
        System.out.println("Operación no permitida en este estado.");
    }

    @Override
    public void agregarEntrada(Compra compra, Entrada entrada) {
        operacionNoPermitida();
    }

    @Override
    public void eliminarEntrada(Compra compra, String idEntrada) {
        operacionNoPermitida();
    }

    @Override
    public void agregarServicio(Compra compra, ServicioAdicional servicio) {
        operacionNoPermitida();
    }

    @Override
    public void eliminarServicio(Compra compra, String idServicio) {
        operacionNoPermitida();
    }

    @Override
    public void cancelar(Compra compra) {
        operacionNoPermitida();
    }

    @Override
    public void confirmar(Compra compra) {
        operacionNoPermitida();
    }

    @Override
    public void registrarPago(Compra compra, Pago pago) {
        operacionNoPermitida();
    }
}