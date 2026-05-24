package model;

/**
 * Estado de una compra cancelada.
 */
public class EstadoCancelada extends EstadoCompraBase {

    @Override
    public String getNombreEstado() {
        return "CANCELADA";
    }
}
