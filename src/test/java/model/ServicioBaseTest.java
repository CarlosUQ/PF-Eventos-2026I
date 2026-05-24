package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioBaseTest {

    private ServicioBase servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioBase("SRV001", "Seguro de Cancelación", 25.0);
    }

    @Test
    void testCreacionServicioBase() {
        assertNotNull(servicio);
        assertEquals("SRV001", servicio.getIdServicio());
        assertEquals("Seguro de Cancelación", servicio.getDescripcion());
        assertEquals(25.0, servicio.getPrecio());
    }

    @Test
    void testGetIdServicio() {
        assertEquals("SRV001", servicio.getIdServicio());
    }

    @Test
    void testGetDescripcion() {
        assertEquals("Seguro de Cancelación", servicio.getDescripcion());
    }

    @Test
    void testGetPrecio() {
        assertEquals(25.0, servicio.getPrecio());
    }

    @Test
    void testImplementaServicioAdicional() {
        assertTrue(servicio instanceof ServicioAdicional);
    }

    @Test
    void testMultiplesServicios() {
        ServicioBase servicio1 = new ServicioBase("SRV001", "Seguro", 25.0);
        ServicioBase servicio2 = new ServicioBase("SRV002", "Estacionamiento", 15.0);
        ServicioBase servicio3 = new ServicioBase("SRV003", "Comida VIP", 50.0);

        assertEquals(25.0, servicio1.getPrecio());
        assertEquals(15.0, servicio2.getPrecio());
        assertEquals(50.0, servicio3.getPrecio());

        assertEquals("Seguro", servicio1.getDescripcion());
        assertEquals("Estacionamiento", servicio2.getDescripcion());
        assertEquals("Comida VIP", servicio3.getDescripcion());
    }

    @Test
    void testPrecioNegativo() {
        ServicioBase servicioNegativo = new ServicioBase("SRV004", "Descuento", -10.0);
        assertEquals(-10.0, servicioNegativo.getPrecio());
    }

    @Test
    void testPrecioCero() {
        ServicioBase servicioCero = new ServicioBase("SRV005", "Regalo", 0.0);
        assertEquals(0.0, servicioCero.getPrecio());
    }

    @Test
    void testIdNoNulo() {
        assertNotNull(servicio.getIdServicio());
        assertFalse(servicio.getIdServicio().equals(""));
    }

    @Test
    void testDescripcionNoNula() {
        assertNotNull(servicio.getDescripcion());
        assertFalse(servicio.getDescripcion().equals(""));
    }

    @Test
    void testPrecioAlto() {
        ServicioBase servicioAlto = new ServicioBase("SRV006", "VIP Premium", 999.99);
        assertEquals(999.99, servicioAlto.getPrecio());
    }

    @Test
    void testCompararServicios() {
        ServicioBase servicio1 = new ServicioBase("SRV001", "Seguro", 25.0);
        ServicioBase servicio2 = new ServicioBase("SRV001", "Seguro", 25.0);

        assertEquals(servicio1.getIdServicio(), servicio2.getIdServicio());
        assertEquals(servicio1.getDescripcion(), servicio2.getDescripcion());
        assertEquals(servicio1.getPrecio(), servicio2.getPrecio());
    }
}
