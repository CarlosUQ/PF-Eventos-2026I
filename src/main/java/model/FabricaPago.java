package model;

/**
 * Fabrica que crea estrategias de pago segun un texto.
 */
public class FabricaPago {

    /**
     * Crea una estrategia de pago.
     *
     * @param tipo nombre del metodo de pago
     * @return estrategia de pago creada
     */
    public static IEstrategiaPago crearPago(String tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Metodo de pago no puede ser nulo");
        }

        // Se usa mayuscula para aceptar valores como "nequi" o "Nequi".
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
