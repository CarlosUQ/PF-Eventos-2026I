package model;

public class PagoNequi implements IEstrategiaPago {

    @Override
    public boolean procesarPago(double monto) {

        return monto > 0;
    }
}