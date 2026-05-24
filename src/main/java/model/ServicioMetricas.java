package model;

/**
 * Calcula metricas basicas del sistema.
 */
public class ServicioMetricas {

    private final SistemaEventos sistema = SistemaEventos.getInstancia();

    /**
     * Obtiene el total de compras registradas.
     *
     * @return cantidad de compras
     */
    public int totalCompras() {
        return sistema.getCompras().size();
    }

    /**
     * Cuenta las compras canceladas.
     *
     * @return cantidad de compras canceladas
     */
    public int comprasCanceladas() {
        int count = 0;

        for (Compra c : sistema.getCompras()) {
            if ("CANCELADA".equals(c.getEstado().getNombreEstado())) {
                count++;
            }
        }

        return count;
    }

    /**
     * Cuenta las compras pagadas.
     *
     * @return cantidad de compras pagadas
     */
    public int comprasPagadas() {
        int count = 0;

        for (Compra c : sistema.getCompras()) {
            if ("PAGADA".equals(c.getEstado().getNombreEstado())) {
                count++;
            }
        }

        return count;
    }

    /**
     * Cuenta las compras confirmadas.
     *
     * @return cantidad de compras confirmadas
     */
    public int comprasConfirmadas() {
        int count = 0;

        for (Compra c : sistema.getCompras()) {
            if ("CONFIRMADA".equals(c.getEstado().getNombreEstado())) {
                count++;
            }
        }

        return count;
    }

    /**
     * Suma los pagos aprobados.
     *
     * @return ingresos totales
     */
    public double ingresosTotales() {
        double total = 0;

        for (Pago p : sistema.getPagos()) {
            if (p.getEstadoPago() == EstadoPago.APROBADO) {
                total += p.getMonto();
            }
        }

        return total;
    }

    /**
     * Obtiene el total de eventos registrados.
     *
     * @return cantidad de eventos
     */
    public int totalEventos() {
        return sistema.getEventos().size();
    }
}
