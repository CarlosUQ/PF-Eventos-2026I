package model;

import java.time.LocalDateTime;

public class Pago {

    private String idPago;

    private IEstrategiaPago estrategiaPago;

    private LocalDateTime fechaPago;

    private double monto;

    private EstadoPago estadoPago;

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


    public boolean procesarPago() {
        boolean resultado = estrategiaPago.procesarPago(monto);

        if (resultado) {
            aprobarPago();
        } else {
            rechazarPago();
        }

        return resultado;
    }


    public void aprobarPago() {
        estadoPago = EstadoPago.APROBADO;
    }

    public void rechazarPago() {
        estadoPago = EstadoPago.RECHAZADO;
    }

    public void reembolsarPago() {
        estadoPago = EstadoPago.REEMBOLSADO;
    }

    public boolean estaAprobado() {
        return estadoPago == EstadoPago.APROBADO;
    }

    public boolean estaRechazado() {
        return estadoPago == EstadoPago.RECHAZADO;
    }

    public boolean estaPendiente() {
        return estadoPago == EstadoPago.PENDIENTE;
    }

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