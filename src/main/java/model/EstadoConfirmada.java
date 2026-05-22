package model;

public class EstadoConfirmada extends EstadoCompraBase {

    @Override
    public void cancelar(Compra compra) {
        compra.setEstado(new EstadoCancelada());
    }

    @Override
    public String getNombreEstado() {
        return "CONFIRMADA";
    }
}