package model;

public class PagoTarjetaDebito implements IEstrategiaPago {

    @Override
    public boolean procesarPago(double monto) {

        return monto > 0;
    }
}
