package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompraTest {

    private Compra compra;
    private Usuario usuario;
    private Evento evento;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioComun("USR001", "Juan Perez", "juan@email.com", "3001234567", "pass");
        Recinto recinto = new Recinto("REC001", "Movistar Arena", "Calle 1", "Bogota");
        evento = new Evento("EV001", "Concierto", "Musica", "Concierto en vivo",
                "Bogota", LocalDateTime.now().plusDays(10), EstadoEvento.PUBLICADO, recinto);

        compra = new Compra.Builder("COMP001")
                .usuario(usuario)
                .evento(evento)
                .build();
    }

    private Entrada crearEntrada(String idEntrada) {
        Zona zona = new Zona("Z001", "VIP", 100, 150.0, TipoZona.VIP);
        Asiento asiento = new Asiento("A-" + idEntrada, "A", 1, EstadoAsiento.DISPONIBLE);
        return new Entrada(idEntrada, zona, asiento, 100.0, EstadoEntrada.ACTIVA);
    }

    private ServicioAdicional crearServicio(String idServicio) {
        return new ServicioBase(idServicio, "Parqueadero", 20.0);
    }

    @Test
    void testCreacionCompraConBuilder() {
        assertNotNull(compra);
        assertEquals("COMP001", compra.getIdCompra());
        assertEquals(usuario, compra.getUsuario());
        assertEquals(evento, compra.getEvento());
        assertNotNull(compra.getFechaCreacion());
    }

    @Test
    void testEstadoInicialEstadoCreada() {
        assertNotNull(compra.getEstado());
        assertEquals("CREADA", compra.getNombreEstado());
    }

    @Test
    void testTotalInicial() {
        assertEquals(0.0, compra.getTotal());
    }

    @Test
    void testAgregarEntrada() {
        Entrada entrada = crearEntrada("ENT001");
        compra.addEntradaInterna(entrada);

        assertEquals(1, compra.getEntradas().size());
        assertEquals(EstadoAsiento.RESERVADO, entrada.getAsiento().getEstadoAsiento());
    }

    @Test
    void testAgregarEntradaNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            compra.addEntradaInterna(null);
        });
    }

    @Test
    void testAgregarEntradaNoDisponible() {
        Entrada entrada = crearEntrada("ENT001");
        entrada.getAsiento().bloquear();

        assertThrows(IllegalStateException.class, () -> {
            compra.addEntradaInterna(entrada);
        });
    }

    @Test
    void testAgregarServicio() {
        compra.addServicioInterno(crearServicio("SRV001"));
        assertEquals(1, compra.getServiciosAdicionales().size());
    }

    @Test
    void testAgregarServicioNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            compra.addServicioInterno(null);
        });
    }

    @Test
    void testRemoverEntrada() {
        Entrada entrada = crearEntrada("ENT001");
        compra.addEntradaInterna(entrada);

        compra.removeEntradaInterna("ENT001");

        assertEquals(0, compra.getEntradas().size());
        assertEquals(EstadoAsiento.DISPONIBLE, entrada.getAsiento().getEstadoAsiento());
    }

    @Test
    void testRemoverServicio() {
        compra.addServicioInterno(crearServicio("SRV001"));
        compra.removeServicioInterno("SRV001");
        assertEquals(0, compra.getServiciosAdicionales().size());
    }

    @Test
    void testCalcularTotalConEntradas() {
        compra.addEntradaInterna(crearEntrada("ENT001"));
        compra.calcularTotal();
        assertEquals(100.0, compra.getTotal());
    }

    @Test
    void testCalcularTotalConServicios() {
        compra.addServicioInterno(crearServicio("SRV001"));
        compra.calcularTotal();
        assertEquals(20.0, compra.getTotal());
    }

    @Test
    void testCalcularTotalConEntradasYServicios() {
        compra.addEntradaInterna(crearEntrada("ENT001"));
        compra.addServicioInterno(crearServicio("SRV001"));
        compra.calcularTotal();
        assertEquals(120.0, compra.getTotal());
    }

    @Test
    void testSetPago() {
        Pago pago = new Pago("P001", new PagoNequi(), LocalDateTime.now(), 100.0, EstadoPago.PENDIENTE);
        compra.setPago(pago);
        assertEquals(pago, compra.getPago());
    }

    @Test
    void testSetEstado() {
        EstadoCompraState nuevoEstado = new EstadoConfirmada();
        compra.setEstado(nuevoEstado);
        assertEquals(nuevoEstado, compra.getEstado());
        assertEquals("CONFIRMADA", compra.getNombreEstado());
    }

    @Test
    void testCompraCanceladaNoPuedeSerReembolsada() {
        Pago pago = new Pago("P001", new PagoNequi(), LocalDateTime.now(), 100.0, EstadoPago.APROBADO);
        compra.setPago(pago);
        compra.setEstado(new EstadoCancelada());

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            compra.reembolsarCompra();
        });

        assertEquals("No se puede reembolsar una compra cancelada.", ex.getMessage());
        assertEquals("CANCELADA", compra.getNombreEstado());
        assertEquals(EstadoPago.APROBADO, pago.getEstadoPago());
    }

    @Test
    void testCompraCanceladaNoPuedeSerConfirmada() {
        compra.setEstado(new EstadoCancelada());

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            compra.confirmarCompra();
        });

        assertEquals("No se puede confirmar una compra en estado CANCELADA.", ex.getMessage());
        assertEquals("CANCELADA", compra.getNombreEstado());
    }

    @Test
    void testCompraConfirmadaNoPuedeSerConfirmadaOtraVez() {
        compra.setEstado(new EstadoConfirmada());

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            compra.confirmarCompra();
        });

        assertEquals("No se puede confirmar una compra en estado CONFIRMADA.", ex.getMessage());
        assertEquals("CONFIRMADA", compra.getNombreEstado());
    }

    @Test
    void testAgregarObservador() {
        ObservadorPrueba observador = new ObservadorPrueba();
        compra.agregarObservador(observador);

        compra.notificarObservadores("Test");

        assertEquals("Test", observador.getUltimoMensaje());
    }

    @Test
    void testEliminarObservador() {
        ObservadorPrueba observador = new ObservadorPrueba();
        compra.agregarObservador(observador);
        compra.eliminarObservador(observador);

        compra.notificarObservadores("Test");

        assertNull(observador.getUltimoMensaje());
    }

    @Test
    void testNotificarObservadores() {
        ObservadorPrueba observador1 = new ObservadorPrueba();
        ObservadorPrueba observador2 = new ObservadorPrueba();

        compra.agregarObservador(observador1);
        compra.agregarObservador(observador2);
        compra.notificarObservadores("Notificacion de prueba");

        assertEquals("Notificacion de prueba", observador1.getUltimoMensaje());
        assertEquals("Notificacion de prueba", observador2.getUltimoMensaje());
    }

    @Test
    void testGetEntradasRetornaCopia() {
        compra.addEntradaInterna(crearEntrada("ENT001"));
        List<Entrada> entradas = compra.getEntradas();
        entradas.clear();

        assertEquals(1, compra.getEntradas().size());
    }

    @Test
    void testGetServiciosRetornaCopia() {
        compra.addServicioInterno(crearServicio("SRV001"));
        List<ServicioAdicional> servicios = compra.getServiciosAdicionales();
        servicios.clear();

        assertEquals(1, compra.getServiciosAdicionales().size());
    }

    @Test
    void testBuilderSinUsuarioThrows() {
        assertThrows(IllegalStateException.class, () -> {
            new Compra.Builder("COMP002")
                    .evento(evento)
                    .build();
        });
    }

    @Test
    void testBuilderSinEventoThrows() {
        assertThrows(IllegalStateException.class, () -> {
            new Compra.Builder("COMP002")
                    .usuario(usuario)
                    .build();
        });
    }

    @Test
    void testBuilderCompleto() {
        Entrada entrada = crearEntrada("ENT001");
        ServicioAdicional servicio = crearServicio("SRV001");
        Pago pago = new Pago("P001", new PagoNequi(), LocalDateTime.now(), 120.0, EstadoPago.PENDIENTE);

        Compra compraCompleta = new Compra.Builder("COMP003")
                .usuario(usuario)
                .evento(evento)
                .agregarEntrada(entrada)
                .agregarServicio(servicio)
                .pago(pago)
                .build();

        assertNotNull(compraCompleta);
        assertEquals("COMP003", compraCompleta.getIdCompra());
        assertEquals(1, compraCompleta.getEntradas().size());
        assertEquals(1, compraCompleta.getServiciosAdicionales().size());
        assertEquals(pago, compraCompleta.getPago());
        assertEquals(120.0, compraCompleta.getTotal());
    }

    private static class ObservadorPrueba implements IObservador {
        private String ultimoMensaje;

        @Override
        public void actualizar(String mensaje) {
            ultimoMensaje = mensaje;
        }

        String getUltimoMensaje() {
            return ultimoMensaje;
        }
    }
}
