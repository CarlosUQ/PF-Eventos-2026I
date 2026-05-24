package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecintoTest {

    private Recinto recinto;
    private Zona zona1;
    private Zona zona2;
    private Zona zona3;

    @BeforeEach
    void setUp() {
        recinto = new Recinto("REC001", "Teatro Nacional", "Calle 10 #5-50", "Bogotá");
        zona1 = new Zona("Z001", "VIP", 100, 50.0, TipoZona.VIP);
        zona2 = new Zona("Z002", "General", 200, 30.0, TipoZona.GENERAL);
        zona3 = new Zona("Z003", "Preferencial", 150, 40.0, TipoZona.PREFERENCIAL);
    }

    @Test
    void testCreacionRecinto() {
        assertNotNull(recinto);
        assertEquals("REC001", recinto.getIdRecinto());
        assertEquals("Teatro Nacional", recinto.getNombre());
        assertEquals("Calle 10 #5-50", recinto.getDireccion());
        assertEquals("Bogotá", recinto.getCiudad());
    }

    @Test
    void testAgregarZona() {
        recinto.agregarZona(zona1);
        assertEquals(1, recinto.obtenerCantidadZonas());
        assertNotNull(recinto.obtenerZonaPorId("Z001"));
    }

    @Test
    void testAgregarMultiplesZonas() {
        recinto.agregarZona(zona1);
        recinto.agregarZona(zona2);
        recinto.agregarZona(zona3);

        assertEquals(3, recinto.obtenerCantidadZonas());
    }

    @Test
    void testObtenerZonaPorId() {
        recinto.agregarZona(zona1);
        recinto.agregarZona(zona2);

        Zona obtenida = recinto.obtenerZonaPorId("Z001");
        assertNotNull(obtenida);
        assertEquals("Z001", obtenida.getIdZona());
        assertEquals("VIP", obtenida.getNombre());
    }

    @Test
    void testObtenerZonaNoExistente() {
        recinto.agregarZona(zona1);
        Zona obtenida = recinto.obtenerZonaPorId("Z999");
        assertNull(obtenida);
    }

    @Test
    void testExisteZona() {
        recinto.agregarZona(zona1);
        assertTrue(recinto.existeZona("Z001"));
        assertFalse(recinto.existeZona("Z999"));
    }

    @Test
    void testEliminarZona() {
        recinto.agregarZona(zona1);
        recinto.agregarZona(zona2);

        boolean eliminada = recinto.eliminarZona("Z001");
        assertTrue(eliminada);
        assertEquals(1, recinto.obtenerCantidadZonas());
        assertFalse(recinto.existeZona("Z001"));
    }

    @Test
    void testEliminarZonaNoExistente() {
        recinto.agregarZona(zona1);
        boolean eliminada = recinto.eliminarZona("Z999");
        assertFalse(eliminada);
    }

    @Test
    void testObtenerCapacidadTotal() {
        recinto.agregarZona(zona1);
        recinto.agregarZona(zona2);
        recinto.agregarZona(zona3);

        int capacidadTotal = recinto.obtenerCapacidadTotal();
        assertEquals(450, capacidadTotal); // 100 + 200 + 150
    }

    @Test
    void testObtenerCapacidadTotalSinZonas() {
        assertEquals(0, recinto.obtenerCapacidadTotal());
    }

    @Test
    void testObtenerCantidadZonas() {
        assertEquals(0, recinto.obtenerCantidadZonas());

        recinto.agregarZona(zona1);
        assertEquals(1, recinto.obtenerCantidadZonas());

        recinto.agregarZona(zona2);
        assertEquals(2, recinto.obtenerCantidadZonas());
    }

    @Test
    void testSetNombre() {
        recinto.setNombre("Teatro Ateneo");
        assertEquals("Teatro Ateneo", recinto.getNombre());
    }

    @Test
    void testSetDireccion() {
        recinto.setDireccion("Calle 15 #10-20");
        assertEquals("Calle 15 #10-20", recinto.getDireccion());
    }

    @Test
    void testSetCiudad() {
        recinto.setCiudad("Medellín");
        assertEquals("Medellín", recinto.getCiudad());
    }

    @Test
    void testGetZonas() {
        recinto.agregarZona(zona1);
        recinto.agregarZona(zona2);

        List<Zona> zonas = recinto.getZonas();
        assertEquals(2, zonas.size());
        assertTrue(zonas.contains(zona1));
        assertTrue(zonas.contains(zona2));
    }

    @Test
    void testMultiplesOperacionesZonas() {
        recinto.agregarZona(zona1);
        recinto.agregarZona(zona2);
        recinto.agregarZona(zona3);

        assertEquals(3, recinto.obtenerCantidadZonas());
        assertEquals(450, recinto.obtenerCapacidadTotal());

        recinto.eliminarZona("Z002");
        assertEquals(2, recinto.obtenerCantidadZonas());
        assertEquals(250, recinto.obtenerCapacidadTotal());

        recinto.eliminarZona("Z001");
        assertEquals(1, recinto.obtenerCantidadZonas());
        assertEquals(150, recinto.obtenerCapacidadTotal());
    }
}
