package model;

/**
 * Define las operaciones que puede permitir o negar un estado de compra.
 */
public interface EstadoCompraState {

    /**
     * Agrega una entrada a la compra.
     *
     * @param compra compra que se modifica
     * @param entrada entrada que se agrega
     */
    void agregarEntrada(Compra compra, Entrada entrada);

    /**
     * Elimina una entrada de la compra.
     *
     * @param compra compra que se modifica
     * @param idEntrada identificador de la entrada
     */
    void eliminarEntrada(Compra compra, String idEntrada);

    /**
     * Agrega un servicio a la compra.
     *
     * @param compra compra que se modifica
     * @param servicio servicio que se agrega
     */
    void agregarServicio(Compra compra, ServicioAdicional servicio);

    /**
     * Elimina un servicio de la compra.
     *
     * @param compra compra que se modifica
     * @param idServicio identificador del servicio
     */
    void eliminarServicio(Compra compra, String idServicio);

    /**
     * Cancela una compra.
     *
     * @param compra compra que se desea cancelar
     */
    void cancelar(Compra compra);

    /**
     * Confirma una compra.
     *
     * @param compra compra que se desea confirmar
     */
    void confirmar(Compra compra);

    /**
     * Registra un pago en la compra.
     *
     * @param compra compra que recibe el pago
     * @param pago pago registrado
     */
    void registrarPago(Compra compra, Pago pago);

    /**
     * Obtiene el nombre del estado.
     *
     * @return nombre del estado
     */
    String getNombreEstado();
}
