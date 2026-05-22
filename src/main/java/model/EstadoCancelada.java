package model;

public class EstadoCancelada extends EstadoCompraBase {

    @Override
    public String getNombreEstado() {
        return "CANCELADA";
    }
}