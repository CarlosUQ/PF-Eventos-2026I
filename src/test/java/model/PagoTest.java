package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PagoTest {

    private Pago pago;

    @BeforeEach
    void setUp() {
        pago = new Pago("P001", new PagoNequi(), LocalDateTime.now(), 100.0, EstadoPago.PENDIENTE);
    }

    @Test
    void testCreacionPago() {
        assertNotNull(pago);
        assertEquals("P001", pago.getIdPago());
        assertEquals(100.0, pago.getMonto());
        assertEquals(EstadoPago.PENDIENTE, pago.getEstadoPago());
        assertNotNull(pago.getFechaPago());
    }

    @Test
    void testEstaPendiente() {
        assertTrue(pago.estaPendiente());
        assertFalse(pago.estaAprobado());
    }

    @Test
    void testAprobarPago() {
        pago.aprobarPago();
        assertEquals(EstadoPago.APROBADO, pago.getEstadoPago());
        assertTrue(pago.estaAprobado());
        assertFalse(pago.estaPendiente());
    }

    @Test
    void testRechazarPago() {
        pago.rechazarPago();
        assertEquals(EstadoPago.RECHAZADO, pago.getEstadoPago());
        assertTrue(pago.estaRechazado());
        assertFalse(pago.estaAprobado());
    }

    @Test
    void testReembolsarPago() {
        pago.reembolsarPago();
        assertEquals(EstadoPago.REEMBOLSADO, pago.getEstadoPago());
        assertTrue(pago.estaReembolsado());
    }

    @Test
    void testProcesarPagoExitoso() {
        boolean resultado = pago.procesarPago();

        assertTrue(resultado);
        assertTrue(pago.estaAprobado());
    }

    @Test
    void testProcesarPagoFallido() {
        Pago pagoFallido = new Pago("P002", new PagoNequi(), LocalDateTime.now(), 0.0, EstadoPago.PENDIENTE);

        boolean resultado = pagoFallido.procesarPago();

        assertFalse(resultado);
        assertTrue(pagoFallido.estaRechazado());
    }

    @Test
    void testSetMonto() {
        pago.setMonto(250.0);
        assertEquals(250.0, pago.getMonto());
    }

    @Test
    void testSetEstrategiaPago() {
        IEstrategiaPago nuevaEstrategia = new PagoPaypal();
        pago.setEstrategiaPago(nuevaEstrategia);
        assertEquals(nuevaEstrategia, pago.getEstrategiaPago());
    }

    @Test
    void testSetFechaPago() {
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(1);
        pago.setFechaPago(nuevaFecha);
        assertEquals(nuevaFecha, pago.getFechaPago());
    }

    @Test
    void testSetEstadoPago() {
        pago.setEstadoPago(EstadoPago.APROBADO);
        assertEquals(EstadoPago.APROBADO, pago.getEstadoPago());
        assertTrue(pago.estaAprobado());
    }

    @Test
    void testToString() {
        assertNotNull(pago.toString());
        assertTrue(pago.toString().contains("P001"));
        assertTrue(pago.toString().contains("100.0"));
    }

    @Test
    void testMontoNegativo() {
        Pago pagoNegativo = new Pago("P002", new PagoNequi(), LocalDateTime.now(), -50.0, EstadoPago.PENDIENTE);
        assertEquals(-50.0, pagoNegativo.getMonto());
    }

    @Test
    void testMontoCero() {
        Pago pagoCero = new Pago("P003", new PagoNequi(), LocalDateTime.now(), 0.0, EstadoPago.PENDIENTE);
        assertEquals(0.0, pagoCero.getMonto());
    }
}
