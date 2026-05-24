package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntradaTest {

    private Entrada entrada;
    private Zona zona;
    private Asiento asiento;

    @BeforeEach
    void setUp() {
        zona = new Zona("Z001", "VIP", 100, 150.0, TipoZona.VIP);
        asiento = new Asiento("A1", "A", 1, EstadoAsiento.DISPONIBLE);
        entrada = new Entrada("ENT001", zona, asiento, 150.0, EstadoEntrada.ACTIVA);
    }

    @Test
    void testCreacionEntrada() {
        assertNotNull(entrada);
        assertEquals("ENT001", entrada.getIdEntrada());
        assertEquals(zona, entrada.getZona());
        assertEquals(asiento, entrada.getAsiento());
        assertEquals(150.0, entrada.getPrecioFinal());
        assertEquals(EstadoEntrada.ACTIVA, entrada.getEstadoEntrada());
    }

    @Test
    void testActivarEntrada() {
        entrada.anularEntrada();
        entrada.activarEntrada();
        assertEquals(EstadoEntrada.ACTIVA, entrada.getEstadoEntrada());
        assertTrue(entrada.estaActiva());
    }

    @Test
    void testUsarEntradaActiva() {
        entrada.usarEntrada();
        assertEquals(EstadoEntrada.USADA, entrada.getEstadoEntrada());
    }

    @Test
    void testUsarEntradaNoActiva() {
        entrada.anularEntrada();
        entrada.usarEntrada();
        assertFalse(EstadoEntrada.USADA.equals(entrada.getEstadoEntrada()));
        assertEquals(EstadoEntrada.ANULADA, entrada.getEstadoEntrada());
    }

    @Test
    void testAnularEntrada() {
        entrada.anularEntrada();
        assertEquals(EstadoEntrada.ANULADA, entrada.getEstadoEntrada());
    }

    @Test
    void testEstaActiva() {
        assertTrue(entrada.estaActiva());
        entrada.anularEntrada();
        assertFalse(entrada.estaActiva());
    }

    @Test
    void testPuedeUsarse() {
        assertTrue(entrada.puedeUsarse());
        entrada.usarEntrada();
        assertFalse(entrada.puedeUsarse());
    }

    @Test
    void testTieneAsientoDisponibleConAsiento() {
        assertTrue(entrada.tieneAsientoDisponible());
        asiento.bloquear();
        assertFalse(entrada.tieneAsientoDisponible());
    }

    @Test
    void testTieneAsientoDisponibleSinAsiento() {
        Entrada entradaSinAsiento = new Entrada("ENT002", zona, null, 100.0, EstadoEntrada.ACTIVA);
        assertTrue(entradaSinAsiento.tieneAsientoDisponible());
    }

    @Test
    void testSetPrecioFinal() {
        entrada.setPrecioFinal(200.0);
        assertEquals(200.0, entrada.getPrecioFinal());
    }

    @Test
    void testSetEstadoEntrada() {
        entrada.setEstadoEntrada(EstadoEntrada.USADA);
        assertEquals(EstadoEntrada.USADA, entrada.getEstadoEntrada());
    }

    @Test
    void testToString() {
        assertNotNull(entrada.toString());
        assertTrue(entrada.toString().contains("ENT001"));
        assertTrue(entrada.toString().contains("150.0"));
    }

    @Test
    void testFlujoCicloVidaEntrada() {
        assertTrue(entrada.estaActiva());
        assertTrue(entrada.puedeUsarse());

        entrada.usarEntrada();
        assertEquals(EstadoEntrada.USADA, entrada.getEstadoEntrada());
        assertFalse(entrada.estaActiva());
        assertFalse(entrada.puedeUsarse());

        entrada.anularEntrada();
        assertEquals(EstadoEntrada.ANULADA, entrada.getEstadoEntrada());
        assertFalse(entrada.estaActiva());
    }

    @Test
    void testPrecioFinalNegativo() {
        Entrada entradaNegativa = new Entrada("ENT003", zona, asiento, -50.0, EstadoEntrada.ACTIVA);
        assertEquals(-50.0, entradaNegativa.getPrecioFinal());
    }

    @Test
    void testPrecioFinalCero() {
        Entrada entradaCero = new Entrada("ENT004", zona, asiento, 0.0, EstadoEntrada.ACTIVA);
        assertEquals(0.0, entradaCero.getPrecioFinal());
    }
}
