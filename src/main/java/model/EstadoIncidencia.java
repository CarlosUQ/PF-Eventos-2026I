package model;

/**
 * Estado de una compra marcada con incidencia.
 */
public class EstadoIncidencia extends EstadoCompraBase {

    @Override
    public String getNombreEstado() {
        return "INCIDENCIA";
    }
}
