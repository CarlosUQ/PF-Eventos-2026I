package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventoTest {

    private Evento evento;
    private Recinto recinto;

    @BeforeEach
    void setUp() {
        recinto = new Recinto("REC001", "Movistar Arena", "Calle 1", "Bogota");
        evento = new Evento("EV001", "Concierto Rock", "Musica", "Concierto de rock en vivo",
                "Bogota", LocalDateTime.now().plusDays(10), EstadoEvento.PUBLICADO, recinto);
    }

    @Test
    void testCreacionEvento() {
        assertNotNull(evento);
        assertEquals("EV001", evento.getIdEvento());
        assertEquals("Concierto Rock", evento.getNombre());
        assertEquals("Musica", evento.getCategoria());
        assertEquals("Concierto de rock en vivo", evento.getDescripcion());
        assertEquals("Bogota", evento.getCiudad());
        assertEquals(EstadoEvento.PUBLICADO, evento.getEstadoEvento());
        assertNotNull(evento.getFechaHora());
        assertEquals(recinto, evento.getRecinto());
    }

    @Test
    void testEstaDisponible() {
        assertTrue(evento.estaDisponible());

        evento.setEstadoEvento(EstadoEvento.CANCELADO);
        assertFalse(evento.estaDisponible());

        evento.setEstadoEvento(EstadoEvento.FINALIZADO);
        assertFalse(evento.estaDisponible());
    }

    @Test
    void testEstaFinalizado() {
        assertFalse(evento.estaFinalizado());
        evento.setEstadoEvento(EstadoEvento.FINALIZADO);
        assertTrue(evento.estaFinalizado());
        assertFalse(evento.estaDisponible());
    }

    @Test
    void testEstaCancelado() {
        assertFalse(evento.estaCancelado());
        evento.setEstadoEvento(EstadoEvento.CANCELADO);
        assertTrue(evento.estaCancelado());
        assertFalse(evento.estaDisponible());
    }

    @Test
    void testSetNombre() {
        evento.setNombre("Concierto Jazz");
        assertEquals("Concierto Jazz", evento.getNombre());
    }

    @Test
    void testSetCategoria() {
        evento.setCategoria("Jazz");
        assertEquals("Jazz", evento.getCategoria());
    }

    @Test
    void testSetDescripcion() {
        String nuevaDescripcion = "Concierto de jazz en el teatro";
        evento.setDescripcion(nuevaDescripcion);
        assertEquals(nuevaDescripcion, evento.getDescripcion());
    }

    @Test
    void testSetCiudad() {
        evento.setCiudad("Medellin");
        assertEquals("Medellin", evento.getCiudad());
    }

    @Test
    void testSetFechaHora() {
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(15);
        evento.setFechaHora(nuevaFecha);
        assertEquals(nuevaFecha, evento.getFechaHora());
    }

    @Test
    void testSetEstadoEvento() {
        evento.setEstadoEvento(EstadoEvento.CANCELADO);
        assertEquals(EstadoEvento.CANCELADO, evento.getEstadoEvento());
        assertTrue(evento.estaCancelado());
    }

    @Test
    void testSetPoliticaCancelacion() {
        String politica = "No reembolso despues de 48 horas";
        evento.setPoliticaCancelacion(politica);
        assertEquals(politica, evento.getPoliticaCancelacion());
    }

    @Test
    void testSetPoliticaReembolso() {
        String politica = "Reembolso completo si se cancela 7 dias antes";
        evento.setPoliticaReembolso(politica);
        assertEquals(politica, evento.getPoliticaReembolso());
    }

    @Test
    void testObtenerZonas() {
        Zona zona = new Zona("Z001", "VIP", 100, 150.0, TipoZona.VIP);
        recinto.agregarZona(zona);

        assertEquals(1, evento.obtenerZonas().size());
        assertTrue(evento.obtenerZonas().contains(zona));
    }

    @Test
    void testSetRecinto() {
        Recinto nuevoRecinto = new Recinto("REC002", "Teatro", "Calle 2", "Medellin");
        evento.setRecinto(nuevoRecinto);
        assertEquals(nuevoRecinto, evento.getRecinto());
    }

    @Test
    void testEstadosDelEvento() {
        evento.setEstadoEvento(EstadoEvento.PUBLICADO);
        assertTrue(evento.estaDisponible());
        assertFalse(evento.estaFinalizado());
        assertFalse(evento.estaCancelado());

        evento.setEstadoEvento(EstadoEvento.FINALIZADO);
        assertFalse(evento.estaDisponible());
        assertTrue(evento.estaFinalizado());
        assertFalse(evento.estaCancelado());

        evento.setEstadoEvento(EstadoEvento.CANCELADO);
        assertFalse(evento.estaDisponible());
        assertFalse(evento.estaFinalizado());
        assertTrue(evento.estaCancelado());
    }

    @Test
    void testPoliticasCancelacionYReembolsoVacias() {
        assertEquals("", evento.getPoliticaCancelacion());
        assertEquals("", evento.getPoliticaReembolso());
    }

    @Test
    void testFechaHoraNoNula() {
        assertNotNull(evento.getFechaHora());
    }
}
