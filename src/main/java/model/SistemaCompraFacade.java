package model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class SistemaCompraFacade {

    private final SistemaEventos sistema;

    public SistemaCompraFacade() {
        this.sistema = SistemaEventos.getInstancia();
    }

    public Compra realizarCompra(
            String idCompra,
            Usuario usuario,
            Evento evento,
            String tipoPago,
            List<Entrada> entradas,
            List<ServicioAdicional> servicios,
            IExportadorReporte exportador
    ) throws IOException {

        // 1. STRATEGY + FACTORY
        IEstrategiaPago estrategiaPago = FabricaPago.crearPago(tipoPago);

        // 2. BUILDER
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

        // 3. OBSERVER
        compra.agregarObservador(usuario);

        sistema.agregarCompra(compra);
        usuario.agregarCompra(compra);

        // 5. PAGO (STRATEGY)
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

        // 6. EXPORTADOR (ADAPTER)
        if (exportador != null) {
            exportador.exportar(compra);
        }

        return compra;
    }

    public Compra obtenerCompra(String idCompra) {
        return sistema.obtenerCompraPorId(idCompra);
    }

    public boolean cancelarCompra(String idCompra) {

        Compra compra = sistema.obtenerCompraPorId(idCompra);

        if (compra != null) {
            compra.cancelarCompra();
            return true;
        }

        return false;
    }
}
