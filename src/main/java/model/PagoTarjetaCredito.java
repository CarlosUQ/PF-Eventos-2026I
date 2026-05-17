package model;

public class PagoTarjetaCredito implements IEstrategiaPago {

    @Override
    public boolean procesarPago(double monto) {

        return monto > 0;
    }
}
