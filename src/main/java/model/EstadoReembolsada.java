package model;

/**
 * Estado de una compra reembolsada.
 */
public class EstadoReembolsada extends EstadoCompraBase {

    @Override
    public String getNombreEstado() {
        return "REEMBOLSADA";
    }
}
