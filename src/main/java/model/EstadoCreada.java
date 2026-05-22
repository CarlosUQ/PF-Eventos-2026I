package model;

public class EstadoCreada extends EstadoCompraBase {

    @Override
    public void agregarEntrada(Compra compra, Entrada entrada) {
        compra.addEntradaInterna(entrada);
        compra.calcularTotal();
    }

    @Override
    public void eliminarEntrada(Compra compra, String idEntrada) {
        compra.removeEntradaInterna(idEntrada);
        compra.calcularTotal();
    }

    @Override
    public void agregarServicio(Compra compra, ServicioAdicional servicio) {
        compra.addServicioInterno(servicio);
        compra.calcularTotal();
    }

    @Override
    public void eliminarServicio(Compra compra, String idServicio) {
        compra.removeServicioInterno(idServicio);
        compra.calcularTotal();
    }

    @Override
    public void cancelar(Compra compra) {
        compra.setEstado(new EstadoCancelada());
    }

    @Override
    public void registrarPago(Compra compra, Pago pago) {
        compra.setPago(pago);

        if (pago.getEstadoPago() == EstadoPago.APROBADO) {
            compra.setEstado(new EstadoPagada());
        }
    }

    @Override
    public String getNombreEstado() {
        return "CREADA";
    }

}
