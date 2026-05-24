package model;

/**
 * Define la forma de procesar un pago.
 */
public interface IEstrategiaPago {

    /**
     * Procesa un pago con un monto.
     *
     * @param monto valor que se desea pagar
     * @return true si el pago fue aprobado
     */
    boolean procesarPago(double monto);
}
