package model;

/**
 * Estado de una compra confirmada.
 */
public class EstadoConfirmada extends EstadoCompraBase {

    @Override
    public void cancelar(Compra compra) {
        throw new IllegalStateException("No se puede cancelar una compra confirmada.");
    }

    @Override
    public String getNombreEstado() {
        return "CONFIRMADA";
    }
}
