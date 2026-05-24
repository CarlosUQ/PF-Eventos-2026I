package model;

/**
 * Representa a un administrador del sistema.
 *
 * El administrador puede gestionar eventos, asientos, pagos,
 * compras e incidencias.
 */
public class Administrador extends Usuario {

    /**
     * Crea un administrador con sus datos basicos.
     *
     * @param idUsuario identificador del administrador
     * @param nombreCompleto nombre completo del administrador
     * @param correoElectronico correo electronico del administrador
     * @param numeroTelefono numero de telefono del administrador
     * @param contrasena contrasena del administrador
     */
    public Administrador(String idUsuario,
                         String nombreCompleto,
                         String correoElectronico,
                         String numeroTelefono,
                         String contrasena) {

        super(idUsuario, nombreCompleto, correoElectronico, numeroTelefono, contrasena);
    }


    /**
     * Cambia el estado de un evento a publicado.
     *
     * @param evento evento que se desea publicar
     */
    public void publicarEvento(Evento evento) {
        if (evento != null) {
            evento.setEstadoEvento(EstadoEvento.PUBLICADO);
        }
    }

    /**
     * Cambia el estado de un evento a pausado.
     *
     * @param evento evento que se desea pausar
     */
    public void pausarEvento(Evento evento) {
        if (evento != null) {
            evento.setEstadoEvento(EstadoEvento.PAUSADO);
        }
    }

    /**
     * Cambia el estado de un evento a cancelado.
     *
     * @param evento evento que se desea cancelar
     */
    public void cancelarEvento(Evento evento) {
        if (evento != null) {
            evento.setEstadoEvento(EstadoEvento.CANCELADO);
        }
    }

    /**
     * Cambia el estado de un evento a finalizado.
     *
     * @param evento evento que se desea finalizar
     */
    public void finalizarEvento(Evento evento) {
        if (evento != null) {
            evento.setEstadoEvento(EstadoEvento.FINALIZADO);
        }
    }


    /**
     * Bloquea un asiento para que no pueda reservarse.
     *
     * @param asiento asiento que se desea bloquear
     */
    public void bloquearAsiento(Asiento asiento) {
        if (asiento != null) {
            asiento.bloquear();
        }
    }

    /**
     * Libera un asiento y lo deja disponible.
     *
     * @param asiento asiento que se desea liberar
     */
    public void liberarAsiento(Asiento asiento) {
        if (asiento != null) {
            asiento.liberar();
        }
    }


    /**
     * Aprueba un pago.
     *
     * @param pago pago que se desea aprobar
     */
    public void aprobarPago(Pago pago) {
        if (pago != null) {
            pago.setEstadoPago(EstadoPago.APROBADO);
        }
    }

    /**
     * Rechaza un pago.
     *
     * @param pago pago que se desea rechazar
     */
    public void rechazarPago(Pago pago) {
        if (pago != null) {
            pago.setEstadoPago(EstadoPago.RECHAZADO);
        }
    }

    /**
     * Marca un pago como reembolsado.
     *
     * @param pago pago que se desea reembolsar
     */
    public void reembolsarPago(Pago pago) {
        if (pago != null) {
            pago.setEstadoPago(EstadoPago.REEMBOLSADO);
        }
    }

    /**
     * Cancela una compra.
     *
     * @param compra compra que se desea cancelar
     */
    public void cancelarCompra(Compra compra) {
        if (compra != null) {
            // La compra aplica sus propias reglas para cambiar de estado.
            compra.cancelarCompra();
        }
    }

    /**
     * Confirma una compra.
     *
     * @param compra compra que se desea confirmar
     */
    public void confirmarCompra(Compra compra) {
        if (compra != null) {
            compra.confirmarCompra();
        }
    }

    /**
     * Registra una incidencia en el sistema.
     *
     * @param incidencia incidencia que se desea guardar
     */
    public void registrarIncidencia(Incidencia incidencia) {
        if (incidencia != null) {
            SistemaEventos.getInstancia().registrarIncidencia(incidencia);
        }
    }
}
