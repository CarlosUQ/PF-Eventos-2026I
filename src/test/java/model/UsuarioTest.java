package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private UsuarioComun usuario;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioComun("USR001", "Juan Perez", "juan@email.com", "3001234567", "password123");
    }

    private Compra crearCompra(String idCompra) {
        Recinto recinto = new Recinto("REC001", "Movistar Arena", "Calle 1", "Bogota");
        Evento evento = new Evento("EV001", "Concierto", "Musica", "Concierto en vivo",
                "Bogota", LocalDateTime.now().plusDays(10), EstadoEvento.PUBLICADO, recinto);

        return new Compra.Builder(idCompra)
                .usuario(usuario)
                .evento(evento)
                .build();
    }

    @Test
    void testCreacionUsuario() {
        assertNotNull(usuario);
        assertEquals("USR001", usuario.getIdUsuario());
        assertEquals("Juan Perez", usuario.getNombreCompleto());
        assertEquals("juan@email.com", usuario.getCorreoElectronico());
        assertEquals("3001234567", usuario.getNumeroTelefono());
        assertEquals("password123", usuario.getContrasena());
    }

    @Test
    void testAgregarMetodoPago() {
        usuario.agregarMetodoPago("Tarjeta Credito");
        List<String> metodos = usuario.getMetodosPagoRegistrados();
        assertTrue(metodos.contains("Tarjeta Credito"));
        assertEquals(1, metodos.size());
    }

    @Test
    void testAgregarMultiplesMetodosPago() {
        usuario.agregarMetodoPago("Tarjeta Credito");
        usuario.agregarMetodoPago("Tarjeta Debito");
        usuario.agregarMetodoPago("PayPal");

        List<String> metodos = usuario.getMetodosPagoRegistrados();
        assertEquals(3, metodos.size());
        assertTrue(metodos.contains("Tarjeta Credito"));
        assertTrue(metodos.contains("Tarjeta Debito"));
        assertTrue(metodos.contains("PayPal"));
    }

    @Test
    void testNoAgregarMetodoPagoDuplicado() {
        usuario.agregarMetodoPago("Tarjeta Credito");
        usuario.agregarMetodoPago("Tarjeta Credito");

        assertEquals(1, usuario.getMetodosPagoRegistrados().size());
    }

    @Test
    void testEliminarMetodoPago() {
        usuario.agregarMetodoPago("Tarjeta Credito");
        usuario.agregarMetodoPago("PayPal");

        boolean eliminado = usuario.eliminarMetodoPago("Tarjeta Credito");
        assertTrue(eliminado);
        assertFalse(usuario.getMetodosPagoRegistrados().contains("Tarjeta Credito"));
        assertEquals(1, usuario.getMetodosPagoRegistrados().size());
    }

    @Test
    void testEliminarMetodoPagoNoExistente() {
        usuario.agregarMetodoPago("PayPal");
        boolean eliminado = usuario.eliminarMetodoPago("Tarjeta Credito");
        assertFalse(eliminado);
    }

    @Test
    void testAgregarCompra() {
        Compra compra = crearCompra("COMP001");
        usuario.agregarCompra(compra);

        assertEquals(1, usuario.getCompras().size());
        assertTrue(usuario.getCompras().contains(compra));
    }

    @Test
    void testNoAgregarCompraNull() {
        usuario.agregarCompra(null);
        assertEquals(0, usuario.getCompras().size());
    }

    @Test
    void testNoAgregarCompraDuplicada() {
        Compra compra = crearCompra("COMP001");
        usuario.agregarCompra(compra);
        usuario.agregarCompra(compra);
        assertEquals(1, usuario.getCompras().size());
    }

    @Test
    void testObtenerCompraPorId() {
        Compra compra = crearCompra("COMP001");
        usuario.agregarCompra(compra);

        assertNotNull(usuario.obtenerCompraPorId("COMP001"));
        assertEquals(compra, usuario.obtenerCompraPorId("COMP001"));
    }

    @Test
    void testObtenerCompraNoExistente() {
        usuario.agregarCompra(crearCompra("COMP001"));
        assertNull(usuario.obtenerCompraPorId("COMP999"));
    }

    @Test
    void testActualizarPerfil() {
        usuario.actualizarPerfil("Carlos Garcia", "carlos@email.com", "3009876543");

        assertEquals("Carlos Garcia", usuario.getNombreCompleto());
        assertEquals("carlos@email.com", usuario.getCorreoElectronico());
        assertEquals("3009876543", usuario.getNumeroTelefono());
    }

    @Test
    void testGetMetodosPagoRegistradosRetornaCopia() {
        usuario.agregarMetodoPago("Tarjeta Credito");
        List<String> metodos = usuario.getMetodosPagoRegistrados();
        metodos.add("PayPal");

        assertEquals(1, usuario.getMetodosPagoRegistrados().size());
        assertFalse(usuario.getMetodosPagoRegistrados().contains("PayPal"));
    }

    @Test
    void testGetComprasRetornaCopia() {
        usuario.agregarCompra(crearCompra("COMP001"));
        List<Compra> compras = usuario.getCompras();
        compras.add(crearCompra("COMP002"));

        assertEquals(1, usuario.getCompras().size());
        assertNull(usuario.obtenerCompraPorId("COMP002"));
    }

    @Test
    void testToString() {
        assertNotNull(usuario.toString());
        assertTrue(usuario.toString().contains("USR001"));
        assertTrue(usuario.toString().contains("Juan Perez"));
    }

    @Test
    void testListasInicialesVacias() {
        UsuarioComun usuarioNuevo = new UsuarioComun("USR002", "Test", "test@email.com", "123", "pass");
        assertEquals(0, usuarioNuevo.getMetodosPagoRegistrados().size());
        assertEquals(0, usuarioNuevo.getCompras().size());
    }
}
