package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZonaTest {

    private Zona zona;
    private Asiento asiento1;
    private Asiento asiento2;
    private Asiento asiento3;

    @BeforeEach
    void setUp() {
        zona = new Zona("Z001", "Zona VIP", 100, 50.0, TipoZona.VIP);
        asiento1 = new Asiento("AS001", "A", 1, EstadoAsiento.DISPONIBLE);
        asiento2 = new Asiento("AS002", "A", 2, EstadoAsiento.DISPONIBLE);
        asiento3 = new Asiento("AS003", "A", 3, EstadoAsiento.DISPONIBLE);
    }

    @Test
    void testCreacionZona() {
        assertNotNull(zona);
        assertEquals("Z001", zona.getIdZona());
        assertEquals("Zona VIP", zona.getNombre());
        assertEquals(100, zona.getCapacidad());
        assertEquals(50.0, zona.getPrecioBase());
        assertEquals(TipoZona.VIP, zona.getTipoZona());
    }

    @Test
    void testAgregarAsiento() {
        zona.agregarAsiento(asiento1);
        assertEquals(1, zona.getAsientos().size());
        assertNotNull(zona.obtenerAsientoPorId("AS001"));
    }

    @Test
    void testObtenerAsientoPorId() {
        zona.agregarAsiento(asiento1);
        zona.agregarAsiento(asiento2);

        Asiento obtenido = zona.obtenerAsientoPorId("AS001");
        assertNotNull(obtenido);
        assertEquals("AS001", obtenido.getIdAsiento());
    }

    @Test
    void testObtenerAsientoNoExistente() {
        zona.agregarAsiento(asiento1);
        Asiento obtenido = zona.obtenerAsientoPorId("AS999");
        assertNull(obtenido);
    }

    @Test
    void testExisteAsiento() {
        zona.agregarAsiento(asiento1);
        assertTrue(zona.existeAsiento("AS001"));
        assertFalse(zona.existeAsiento("AS999"));
    }

    @Test
    void testEliminarAsiento() {
        zona.agregarAsiento(asiento1);
        zona.agregarAsiento(asiento2);
        assertEquals(2, zona.getAsientos().size());

        boolean eliminado = zona.eliminarAsiento("AS001");
        assertTrue(eliminado);
        assertEquals(1, zona.getAsientos().size());
        assertFalse(zona.existeAsiento("AS001"));
    }

    @Test
    void testEliminarAsientoNoExistente() {
        zona.agregarAsiento(asiento1);
        boolean eliminado = zona.eliminarAsiento("AS999");
        assertFalse(eliminado);
    }

    @Test
    void testConsultarAsientosDisponibles() {
        zona.agregarAsiento(asiento1);
        zona.agregarAsiento(asiento2);
        zona.agregarAsiento(asiento3);

        asiento2.reservar();

        List<Asiento> disponibles = zona.consultarAsientosDisponibles();
        assertEquals(2, disponibles.size());
        assertTrue(disponibles.contains(asiento1));
        assertFalse(disponibles.contains(asiento2));
        assertTrue(disponibles.contains(asiento3));
    }

    @Test
    void testObtenerCantidadDisponible() {
        zona.agregarAsiento(asiento1);
        zona.agregarAsiento(asiento2);
        zona.agregarAsiento(asiento3);

        assertEquals(3, zona.obtenerCantidadDisponible());

        asiento1.reservar();
        assertEquals(2, zona.obtenerCantidadDisponible());

        asiento2.vender();
        assertEquals(1, zona.obtenerCantidadDisponible());
    }

    @Test
    void testEstaLlena() {
        zona.agregarAsiento(asiento1);
        zona.agregarAsiento(asiento2);

        assertFalse(zona.estaLlena());

        asiento1.bloquear();
        asiento2.bloquear();

        assertTrue(zona.estaLlena());
    }

    @Test
    void testConsultarOcupacion() {
        zona.agregarAsiento(asiento1);
        zona.agregarAsiento(asiento2);
        zona.agregarAsiento(asiento3);

        assertEquals(0, zona.consultarOcupacion());

        asiento1.reservar();
        asiento2.vender();
        assertEquals(2, zona.consultarOcupacion());
    }

    @Test
    void testSetNombre() {
        zona.setNombre("Zona General");
        assertEquals("Zona General", zona.getNombre());
    }

    @Test
    void testSetCapacidad() {
        zona.setCapacidad(200);
        assertEquals(200, zona.getCapacidad());
    }

    @Test
    void testSetPrecioBase() {
        zona.setPrecioBase(75.5);
        assertEquals(75.5, zona.getPrecioBase());
    }

    @Test
    void testSetTipoZona() {
        zona.setTipoZona(TipoZona.GENERAL);
        assertEquals(TipoZona.GENERAL, zona.getTipoZona());
    }
}
