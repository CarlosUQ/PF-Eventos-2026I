package model;

public class PagoDaviplata implements IEstrategiaPago {

    @Override
    public boolean procesarPago(double monto) {

        return monto > 0;
    }
}
