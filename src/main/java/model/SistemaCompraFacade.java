package model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Fachada para realizar operaciones completas de compra.
 *
 * Coordina entradas, servicios, pagos, registro en sistema y exportacion.
 */
public class SistemaCompraFacade {

    private final SistemaEventos sistema;

    /**
     * Crea la fachada usando la instancia principal del sistema.
     */
    public SistemaCompraFacade() {
        this.sistema = SistemaEventos.getInstancia();
    }

    /**
     * Realiza una compra completa.
     *
     * @param idCompra identificador de la compra
     * @param usuario usuario que compra
     * @param evento evento comprado
     * @param tipoPago metodo de pago
     * @param entradas entradas de la compra
     * @param servicios servicios adicionales
     * @param exportador exportador opcional del reporte
     * @return compra creada
     * @throws IOException si falla la exportacion
     */
    public Compra realizarCompra(
            String idCompra,
            Usuario usuario,
            Evento evento,
            String tipoPago,
            List<Entrada> entradas,
            List<ServicioAdicional> servicios,
            IExportadorReporte exportador
    ) throws IOException {

        // Se crea la estrategia de pago a partir del tipo elegido.
        IEstrategiaPago estrategiaPago = FabricaPago.crearPago(tipoPago);

        // Se usa el builder para armar la compra paso a paso.
        Compra.Builder builder = new Compra.Builder(idCompra)
                .usuario(usuario)
                .evento(evento);

        if (entradas == null || entradas.isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos una entrada");
        }

        for (Entrada entrada : entradas) {
            builder.agregarEntrada(entrada);
        }

        if (servicios != null) {
            for (ServicioAdicional s : servicios) {
                builder.agregarServicio(s);
            }
        }

        Compra compra = builder.build();

        // El usuario queda como observador para recibir mensajes de su compra.
        compra.agregarObservador(usuario);

        sistema.agregarCompra(compra);
        usuario.agregarCompra(compra);

        // Se procesa el pago con la estrategia seleccionada.
        Pago pago = new Pago(
                "P-" + idCompra,
                estrategiaPago,
                LocalDateTime.now(),
                compra.getTotal(),
                EstadoPago.PENDIENTE
        );

        boolean aprobado = pago.procesarPago();

        if (aprobado) {
            compra.registrarPago(pago);
            sistema.registrarPago(pago);
        } else {
            compra.notificarObservadores(
                    "Pago rechazado para la compra " + idCompra
            );
        }

        // Si hay exportador, se genera el archivo de la compra.
        if (exportador != null) {
            exportador.exportar(compra);
        }

        return compra;
    }

    /**
     * Busca una compra por su identificador.
     *
     * @param idCompra identificador de la compra
     * @return compra encontrada o null
     */
    public Compra obtenerCompra(String idCompra) {
        return sistema.obtenerCompraPorId(idCompra);
    }

    /**
     * Cancela una compra por su identificador.
     *
     * @param idCompra identificador de la compra
     * @return true si se pudo cancelar
     */
    public boolean cancelarCompra(String idCompra) {

        Compra compra = sistema.obtenerCompraPorId(idCompra);

        if (compra != null) {
            try {
                compra.cancelarCompra();
                return true;
            } catch (IllegalStateException ex) {
                return false;
            }
        }

        return false;
    }
}
