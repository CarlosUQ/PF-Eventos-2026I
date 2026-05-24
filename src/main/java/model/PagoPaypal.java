package model;

/**
 * Estrategia de pago con PayPal.
 */
public class PagoPaypal implements IEstrategiaPago {

    /**
     * Aprueba el pago si el monto es mayor que cero.
     *
     * @param monto valor que se desea pagar
     * @return true si el monto es valido
     */
    @Override
    public boolean procesarPago(double monto) {

        return monto > 0;
    }
}
