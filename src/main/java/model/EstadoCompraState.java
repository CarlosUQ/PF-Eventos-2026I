package model;

public interface EstadoCompraState {

    void agregarEntrada(Compra compra, Entrada entrada);

    void eliminarEntrada(Compra compra, String idEntrada);

    void agregarServicio(Compra compra, ServicioAdicional servicio);

    void eliminarServicio(Compra compra, String idServicio);

    void cancelar(Compra compra);

    void confirmar(Compra compra);

    void registrarPago(Compra compra, Pago pago);

    String getNombreEstado();
}