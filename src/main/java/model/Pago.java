package model;

import java.time.LocalDateTime;

/**
 * Representa un pago realizado para una compra.
 *
 * El pago usa una estrategia para procesarse segun el metodo elegido.
 */
public class Pago {

    private String idPago;

    private IEstrategiaPago estrategiaPago;

    private LocalDateTime fechaPago;

    private double monto;

    private EstadoPago estadoPago;

    /**
     * Crea un pago con su metodo, fecha, monto y estado.
     *
     * @param idPago identificador del pago
     * @param estrategiaPago estrategia usada para procesar el pago
     * @param fechaPago fecha del pago
     * @param monto valor del pago
     * @param estadoPago estado inicial del pago
     */
    public Pago(String idPago,
                IEstrategiaPago estrategiaPago,
                LocalDateTime fechaPago,
                double monto,
                EstadoPago estadoPago) {

        this.idPago = idPago;
        this.estrategiaPago = estrategiaPago;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.estadoPago = estadoPago;
    }


    /**
     * Procesa el pago usando la estrategia configurada.
     *
     * @return true si el pago fue aprobado
     */
    public boolean procesarPago() {
        // La estrategia decide si el pago es valido segun el monto.
        boolean resultado = estrategiaPago.procesarPago(monto);

        if (resultado) {
            aprobarPago();
        } else {
            rechazarPago();
        }

        return resultado;
    }


    /**
     * Marca el pago como aprobado.
     */
    public void aprobarPago() {
        estadoPago = EstadoPago.APROBADO;
    }

    /**
     * Marca el pago como rechazado.
     */
    public void rechazarPago() {
        estadoPago = EstadoPago.RECHAZADO;
    }

    /**
     * Marca el pago como reembolsado.
     */
    public void reembolsarPago() {
        estadoPago = EstadoPago.REEMBOLSADO;
    }

    /**
     * Indica si el pago esta aprobado.
     *
     * @return true si el estado es aprobado
     */
    public boolean estaAprobado() {
        return estadoPago == EstadoPago.APROBADO;
    }

    /**
     * Indica si el pago esta rechazado.
     *
     * @return true si el estado es rechazado
     */
    public boolean estaRechazado() {
        return estadoPago == EstadoPago.RECHAZADO;
    }

    /**
     * Indica si el pago esta pendiente.
     *
     * @return true si el estado es pendiente
     */
    public boolean estaPendiente() {
        return estadoPago == EstadoPago.PENDIENTE;
    }

    /**
     * Indica si el pago esta reembolsado.
     *
     * @return true si el estado es reembolsado
     */
    public boolean estaReembolsado() {
        return estadoPago == EstadoPago.REEMBOLSADO;
    }


    public String getIdPago() {
        return idPago;
    }

    public IEstrategiaPago getEstrategiaPago() {
        return estrategiaPago;
    }

    public void setEstrategiaPago(IEstrategiaPago estrategiaPago) {
        this.estrategiaPago = estrategiaPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago='" + idPago + '\'' +
                ", monto=" + monto +
                ", estadoPago=" + estadoPago +
                '}';
    }
}
