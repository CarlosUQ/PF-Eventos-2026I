package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FabricaPagoTest {

    @Test
    void testCrearPagoNequi() {
        IEstrategiaPago pago = FabricaPago.crearPago("NEQUI");
        assertNotNull(pago);
        assertTrue(pago instanceof PagoNequi);
    }

    @Test
    void testCrearPagoDaviplata() {
        IEstrategiaPago pago = FabricaPago.crearPago("DAVIPLATA");
        assertNotNull(pago);
        assertTrue(pago instanceof PagoDaviplata);
    }

    @Test
    void testCrearPagoPaypal() {
        IEstrategiaPago pago = FabricaPago.crearPago("PAYPAL");
        assertNotNull(pago);
        assertTrue(pago instanceof PagoPaypal);
    }

    @Test
    void testCrearPagoPSE() {
        IEstrategiaPago pago = FabricaPago.crearPago("PSE");
        assertNotNull(pago);
        assertTrue(pago instanceof PagoPSE);
    }

    @Test
    void testCrearPagoTarjetaCredito() {
        IEstrategiaPago pago = FabricaPago.crearPago("TARJETA_CREDITO");
        assertNotNull(pago);
        assertTrue(pago instanceof PagoTarjetaCredito);
    }

    @Test
    void testCrearPagoTarjetaDebito() {
        IEstrategiaPago pago = FabricaPago.crearPago("TARJETA_DEBITO");
        assertNotNull(pago);
        assertTrue(pago instanceof PagoTarjetaDebito);
    }

    @Test
    void testCrearPagoMinusculas() {
        IEstrategiaPago pago = FabricaPago.crearPago("nequi");
        assertNotNull(pago);
        assertTrue(pago instanceof PagoNequi);
    }

    @Test
    void testCrearPagoMixto() {
        IEstrategiaPago pago = FabricaPago.crearPago("PayPal");
        assertNotNull(pago);
        assertTrue(pago instanceof PagoPaypal);
    }

    @Test
    void testCrearPagoNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            FabricaPago.crearPago(null);
        });
    }

    @Test
    void testCrearPagoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            FabricaPago.crearPago("METODO_INVALIDO");
        });
    }

    @Test
    void testCrearPagoVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            FabricaPago.crearPago("");
        });
    }

    @Test
    void testTodosPagosCreados() {
        String[] tipos = {"NEQUI", "DAVIPLATA", "PAYPAL", "PSE", "TARJETA_CREDITO", "TARJETA_DEBITO"};

        for (String tipo : tipos) {
            assertNotNull(FabricaPago.crearPago(tipo));
        }
    }

    @Test
    void testPagosDiferentes() {
        IEstrategiaPago pago1 = FabricaPago.crearPago("NEQUI");
        IEstrategiaPago pago2 = FabricaPago.crearPago("DAVIPLATA");

        assertFalse(pago1.getClass().equals(pago2.getClass()));
    }

    @Test
    void testPagosMismoTipoDiferentes() {
        IEstrategiaPago pago1 = FabricaPago.crearPago("NEQUI");
        IEstrategiaPago pago2 = FabricaPago.crearPago("NEQUI");

        assertNotNull(pago1);
        assertNotNull(pago2);
        assertEquals(pago1.getClass(), pago2.getClass());
    }
}
