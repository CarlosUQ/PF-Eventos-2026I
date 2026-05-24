package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IncidenciaTest {

    private Incidencia incidencia;
    private LocalDateTime fecha;

    @BeforeEach
    void setUp() {
        fecha = LocalDateTime.now();
        incidencia = new Incidencia("INC001", "FALTA_PAGO", "Cliente no pagó la entrada", fecha, "COMPRA");
    }

    @Test
    void testCreacionIncidencia() {
        assertNotNull(incidencia);
        assertEquals("INC001", incidencia.getIdIncidencia());
        assertEquals("FALTA_PAGO", incidencia.getTipo());
        assertEquals("Cliente no pagó la entrada", incidencia.getDescripcion());
        assertEquals(fecha, incidencia.getFecha());
        assertEquals("COMPRA", incidencia.getEntidadAfectada());
    }

    @Test
    void testEsDeTipo() {
        assertTrue(incidencia.esDeTipo("FALTA_PAGO"));
        assertTrue(incidencia.esDeTipo("falta_pago"));
        assertTrue(incidencia.esDeTipo("FALTA_Pago"));
        assertFalse(incidencia.esDeTipo("ERROR_SISTEMA"));
    }

    @Test
    void testAfectaEntidad() {
        assertTrue(incidencia.afectaEntidad("COMPRA"));
        assertTrue(incidencia.afectaEntidad("compra"));
        assertTrue(incidencia.afectaEntidad("CoMpRa"));
        assertFalse(incidencia.afectaEntidad("EVENTO"));
    }

    @Test
    void testSetTipo() {
        incidencia.setTipo("ERROR_SISTEMA");
        assertEquals("ERROR_SISTEMA", incidencia.getTipo());
        assertTrue(incidencia.esDeTipo("ERROR_SISTEMA"));
    }

    @Test
    void testSetDescripcion() {
        String nuevaDescripcion = "Error al procesar el pago";
        incidencia.setDescripcion(nuevaDescripcion);
        assertEquals(nuevaDescripcion, incidencia.getDescripcion());
    }

    @Test
    void testSetFecha() {
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(1);
        incidencia.setFecha(nuevaFecha);
        assertEquals(nuevaFecha, incidencia.getFecha());
    }

    @Test
    void testSetEntidadAfectada() {
        incidencia.setEntidadAfectada("EVENTO");
        assertEquals("EVENTO", incidencia.getEntidadAfectada());
        assertTrue(incidencia.afectaEntidad("EVENTO"));
    }

    @Test
    void testToString() {
        assertNotNull(incidencia.toString());
        assertTrue(incidencia.toString().contains("INC001"));
        assertTrue(incidencia.toString().contains("FALTA_PAGO"));
    }

    @Test
    void testMultiplosTerrorsEnSistema() {
        Incidencia incidencia1 = new Incidencia("INC002", "ASIENTO_DOBLE_RESERVA", "Asiento reservado dos veces", LocalDateTime.now(), "ASIENTO");
        Incidencia incidencia2 = new Incidencia("INC003", "PAGO_DUPLICADO", "Pago procesado dos veces", LocalDateTime.now(), "PAGO");

        assertTrue(incidencia1.esDeTipo("ASIENTO_DOBLE_RESERVA"));
        assertTrue(incidencia2.esDeTipo("PAGO_DUPLICADO"));
        assertTrue(incidencia1.afectaEntidad("ASIENTO"));
        assertTrue(incidencia2.afectaEntidad("PAGO"));
    }

    @Test
    void testFechaActual() {
        LocalDateTime ahora = LocalDateTime.now();
        Incidencia incidenciaAhora = new Incidencia("INC004", "TIPO", "Descripción", ahora, "ENTIDAD");
        assertEquals(ahora, incidenciaAhora.getFecha());
    }

    @Test
    void testIdIncidenciaNoNulo() {
        assertNotNull(incidencia.getIdIncidencia());
        assertEquals("INC001", incidencia.getIdIncidencia());
    }

    @Test
    void testIncidenciaCompleta() {
        Incidencia incidenciaCompleta = new Incidencia("INC005", "ERROR_VALIDACION", "Datos inválidos en formulario", LocalDateTime.now().minusHours(2), "USUARIO");

        assertNotNull(incidenciaCompleta.getIdIncidencia());
        assertNotNull(incidenciaCompleta.getTipo());
        assertNotNull(incidenciaCompleta.getDescripcion());
        assertNotNull(incidenciaCompleta.getFecha());
        assertNotNull(incidenciaCompleta.getEntidadAfectada());
    }

    @Test
    void testCaseSensitivityEsDeTipo() {
        assertTrue(incidencia.esDeTipo("FALTA_PAGO"));
        assertTrue(incidencia.esDeTipo("falta_pago"));
        assertTrue(incidencia.esDeTipo("FaLtA_pAgO"));
    }

    @Test
    void testCaseSensitivityAfectaEntidad() {
        assertTrue(incidencia.afectaEntidad("COMPRA"));
        assertTrue(incidencia.afectaEntidad("compra"));
        assertTrue(incidencia.afectaEntidad("CoMpRa"));
    }
}
