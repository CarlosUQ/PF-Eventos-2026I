package model;

public class ServicioMetricas {

    private final SistemaEventos sistema = SistemaEventos.getInstancia();

    public int totalCompras() {
        return sistema.getCompras().size();
    }

    public int comprasCanceladas() {
        int count = 0;

        for (Compra c : sistema.getCompras()) {
            if ("CANCELADA".equals(c.getEstado().getNombreEstado())) {
                count++;
            }
        }

        return count;
    }

    public int comprasPagadas() {
        int count = 0;

        for (Compra c : sistema.getCompras()) {
            if ("PAGADA".equals(c.getEstado().getNombreEstado())) {
                count++;
            }
        }

        return count;
    }

    public int comprasConfirmadas() {
        int count = 0;

        for (Compra c : sistema.getCompras()) {
            if ("CONFIRMADA".equals(c.getEstado().getNombreEstado())) {
                count++;
            }
        }

        return count;
    }

    public double ingresosTotales() {
        double total = 0;

        for (Pago p : sistema.getPagos()) {
            if (p.getEstadoPago() == EstadoPago.APROBADO) {
                total += p.getMonto();
            }
        }

        return total;
    }

    public int totalEventos() {
        return sistema.getEventos().size();
    }
}