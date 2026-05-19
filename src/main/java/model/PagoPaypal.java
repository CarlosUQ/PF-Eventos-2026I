package model;

public class PagoPaypal implements IEstrategiaPago {

    @Override
    public boolean procesarPago(double monto) {

        return monto > 0;
    }
}