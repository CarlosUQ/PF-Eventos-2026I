package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsientoTest {

    private Asiento asiento;

    @BeforeEach
    void setUp() {
        asiento = new Asiento("AS001", "A", 1, EstadoAsiento.DISPONIBLE);
    }

    @Test
    void testCreacionAsiento() {
        assertNotNull(asiento);
        assertEquals("AS001", asiento.getIdAsiento());
        assertEquals("A", asiento.getFila());
        assertEquals(1, asiento.getNumero());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstadoAsiento());
    }

    @Test
    void testEstaDisponible() {
        assertTrue(asiento.estaDisponible());
    }

    @Test
    void testReservar() {
        assertTrue(asiento.estaDisponible());
        asiento.reservar();
        assertFalse(asiento.estaDisponible());
        assertEquals(EstadoAsiento.RESERVADO, asiento.getEstadoAsiento());
    }

    @Test
    void testVender() {
        asiento.vender();
        assertEquals(EstadoAsiento.VENDIDO, asiento.getEstadoAsiento());
    }

    @Test
    void testVenderDesdeReservado() {
        asiento.reservar();
        asiento.vender();
        assertEquals(EstadoAsiento.VENDIDO, asiento.getEstadoAsiento());
    }

    @Test
    void testBloquear() {
        asiento.bloquear();
        assertEquals(EstadoAsiento.BLOQUEADO, asiento.getEstadoAsiento());
        assertFalse(asiento.estaDisponible());
    }

    @Test
    void testLiberar() {
        asiento.reservar();
        asiento.liberar();
        assertTrue(asiento.estaDisponible());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstadoAsiento());
    }

    @Test
    void testCambiarEstado() {
        asiento.cambiarEstado(EstadoAsiento.RESERVADO);
        assertEquals(EstadoAsiento.RESERVADO, asiento.getEstadoAsiento());

        asiento.cambiarEstado(EstadoAsiento.VENDIDO);
        assertEquals(EstadoAsiento.VENDIDO, asiento.getEstadoAsiento());
    }

    @Test
    void testSetFila() {
        asiento.setFila("B");
        assertEquals("B", asiento.getFila());
    }

    @Test
    void testSetNumero() {
        asiento.setNumero(5);
        assertEquals(5, asiento.getNumero());
    }

    @Test
    void testReservarAsientoNoDisponible() {
        asiento.bloquear();
        asiento.reservar();
        assertEquals(EstadoAsiento.BLOQUEADO, asiento.getEstadoAsiento());
    }

    @Test
    void testToString() {
        assertNotNull(asiento.toString());
        assertTrue(asiento.toString().contains("AS001"));
    }
}
