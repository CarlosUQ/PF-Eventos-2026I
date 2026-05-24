package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorTest {

    private Administrador admin;
    private Evento evento;
    private Asiento asiento;
    private Pago pago;
    private Compra compra;

    @BeforeEach
    void setUp() {
        admin = new Administrador("ADM001", "Admin User", "admin@email.com", "3001111111", "adminpass");
        Recinto recinto = new Recinto("REC001", "Movistar Arena", "Calle 1", "Bogota");
        evento = new Evento("EV001", "Concierto", "Musica", "Concierto en vivo",
                "Bogota", LocalDateTime.now().plusDays(10), EstadoEvento.BORRADOR, recinto);
        asiento = new Asiento("A1", "A", 1, EstadoAsiento.DISPONIBLE);
        pago = new Pago("P001", new PagoNequi(), LocalDateTime.now(), 100.0, EstadoPago.PENDIENTE);
        compra = new Compra.Builder("COMP001")
                .usuario(new UsuarioComun("USR001", "Juan Perez", "juan@email.com", "3001234567", "pass"))
                .evento(evento)
                .build();
    }

    @Test
    void testCreacionAdministrador() {
        assertNotNull(admin);
        assertEquals("ADM001", admin.getIdUsuario());
        assertEquals("Admin User", admin.getNombreCompleto());
    }

    @Test
    void testPublicarEvento() {
        admin.publicarEvento(evento);
        assertEquals(EstadoEvento.PUBLICADO, evento.getEstadoEvento());
    }

    @Test
    void testPublicarEventoNulo() {
        admin.publicarEvento(null);
        assertNotNull(admin);
    }

    @Test
    void testPausarEvento() {
        admin.pausarEvento(evento);
        assertEquals(EstadoEvento.PAUSADO, evento.getEstadoEvento());
    }

    @Test
    void testCancelarEvento() {
        admin.cancelarEvento(evento);
        assertEquals(EstadoEvento.CANCELADO, evento.getEstadoEvento());
    }

    @Test
    void testFinalizarEvento() {
        admin.finalizarEvento(evento);
        assertEquals(EstadoEvento.FINALIZADO, evento.getEstadoEvento());
    }

    @Test
    void testBloquearAsiento() {
        admin.bloquearAsiento(asiento);
        assertEquals(EstadoAsiento.BLOQUEADO, asiento.getEstadoAsiento());
        assertFalse(asiento.estaDisponible());
    }

    @Test
    void testLiberarAsiento() {
        asiento.bloquear();
        admin.liberarAsiento(asiento);
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstadoAsiento());
        assertTrue(asiento.estaDisponible());
    }

    @Test
    void testAprobarPago() {
        admin.aprobarPago(pago);
        assertEquals(EstadoPago.APROBADO, pago.getEstadoPago());
    }

    @Test
    void testRechazarPago() {
        admin.rechazarPago(pago);
        assertEquals(EstadoPago.RECHAZADO, pago.getEstadoPago());
    }

    @Test
    void testReembolsarPago() {
        admin.reembolsarPago(pago);
        assertEquals(EstadoPago.REEMBOLSADO, pago.getEstadoPago());
    }

    @Test
    void testCancelarCompra() {
        admin.cancelarCompra(compra);
        assertEquals("CANCELADA", compra.getNombreEstado());
    }

    @Test
    void testConfirmarCompra() {
        compra.setEstado(new EstadoPagada());
        admin.confirmarCompra(compra);
        assertEquals("CONFIRMADA", compra.getNombreEstado());
    }

    @Test
    void testRegistrarIncidencia() {
        Incidencia incidencia = new Incidencia("INC-ADMIN-001", "Pago", "Pago rechazado",
                LocalDateTime.now(), "P001");
        admin.registrarIncidencia(incidencia);
        assertEquals(incidencia, SistemaEventos.getInstancia().obtenerIncidenciaPorId("INC-ADMIN-001"));
    }

    @Test
    void testAdminHeredaDeUsuario() {
        assertTrue(admin instanceof Usuario);
        admin.agregarMetodoPago("Tarjeta Credito");
        assertTrue(admin.getMetodosPagoRegistrados().contains("Tarjeta Credito"));
    }
}
