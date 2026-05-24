package model;

public class FabricaPago {

    public static IEstrategiaPago crearPago(String tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Metodo de pago no puede ser nulo");
        }

        switch (tipo.toUpperCase()) {

            case "NEQUI":
                return new PagoNequi();

            case "DAVIPLATA":
                return new PagoDaviplata();

            case "PAYPAL":
                return new PagoPaypal();

            case "PSE":
                return new PagoPSE();

            case "TARJETA_CREDITO":
                return new PagoTarjetaCredito();

            case "TARJETA_DEBITO":
                return new PagoTarjetaDebito();

            default:
                throw new IllegalArgumentException("Método de pago no válido: " + tipo);
        }
    }
}

