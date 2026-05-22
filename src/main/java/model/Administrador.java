package model;

public class Administrador extends Usuario {

    public Administrador(String idUsuario,
                         String nombreCompleto,
                         String correoElectronico,
                         String numeroTelefono,
                         String contrasena) {

        super(idUsuario, nombreCompleto, correoElectronico, numeroTelefono, contrasena);
    }


    public void publicarEvento(Evento evento) {
        if (evento != null) {
            evento.setEstadoEvento(EstadoEvento.PUBLICADO);
        }
    }

    public void pausarEvento(Evento evento) {
        if (evento != null) {
            evento.setEstadoEvento(EstadoEvento.PAUSADO);
        }
    }

    public void cancelarEvento(Evento evento) {
        if (evento != null) {
            evento.setEstadoEvento(EstadoEvento.CANCELADO);
        }
    }

    public void finalizarEvento(Evento evento) {
        if (evento != null) {
            evento.setEstadoEvento(EstadoEvento.FINALIZADO);
        }
    }


    public void bloquearAsiento(Asiento asiento) {
        if (asiento != null) {
            asiento.bloquear(); // antes: setEstadoAsiento
        }
    }

    public void liberarAsiento(Asiento asiento) {
        if (asiento != null) {
            asiento.liberar(); // antes: setEstadoAsiento
        }
    }


    public void aprobarPago(Pago pago) {
        if (pago != null) {
            pago.setEstadoPago(EstadoPago.APROBADO);
        }
    }

    public void rechazarPago(Pago pago) {
        if (pago != null) {
            pago.setEstadoPago(EstadoPago.RECHAZADO);
        }
    }

    public void reembolsarPago(Pago pago) {
        if (pago != null) {
            pago.setEstadoPago(EstadoPago.REEMBOLSADO);
        }
    }

    public void cancelarCompra(Compra compra) {
        if (compra != null) {
            compra.cancelarCompra(); // antes: setEstadoCompra
        }
    }

    public void confirmarCompra(Compra compra) {
        if (compra != null) {
            compra.confirmarCompra(); // antes: setEstadoCompra
        }
    }

    public void registrarIncidencia(Incidencia incidencia) {
        if (incidencia != null) {
            SistemaEventos.getInstancia().registrarIncidencia(incidencia);
        }
    }
}