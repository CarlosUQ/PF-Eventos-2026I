package model;

/**
 * Estado de una compra que ya fue pagada.
 */
public class EstadoPagada extends EstadoCompraBase {

    @Override
    public void confirmar(Compra compra) {
        compra.setEstado(new EstadoConfirmada());
    }

    @Override
    public void cancelar(Compra compra) {
        compra.setEstado(new EstadoCancelada());
    }

    @Override
    public void registrarPago(Compra compra, Pago pago) {
        System.out.println("Ya está pagada.");
    }


    @Override
    public String getNombreEstado() {
        return "PAGADA";
    }
}
