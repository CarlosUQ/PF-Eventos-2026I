package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra implements ISujeto {

    private String idCompra;
    private Usuario usuario;
    private Evento evento;
    private LocalDateTime fechaCreacion;
    private double total;

    private EstadoCompraState estado;

    private List<Entrada> entradas;
    private List<ServicioAdicional> serviciosAdicionales;

    private Pago pago;

    private List<IObservador> observadores = new ArrayList<>();

    @Override
    public void agregarObservador(IObservador obs) {
        observadores.add(obs);
    }

    @Override
    public void eliminarObservador(IObservador obs) {
        observadores.remove(obs);
    }

    @Override
    public void notificarObservadores(String mensaje) {
        for (IObservador obs : observadores) {
            obs.actualizar(mensaje);
        }
    }

    private Compra(Builder builder) {

        this.idCompra = builder.idCompra;
        this.usuario = builder.usuario;
        this.evento = builder.evento;
        this.fechaCreacion = builder.fechaCreacion;

        this.entradas = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();

        for (Entrada entrada : builder.entradas) {
            addEntradaInterna(entrada);
        }

        for (ServicioAdicional servicio : builder.serviciosAdicionales) {
            addServicioInterno(servicio);
        }

        this.pago = builder.pago;

        // estado inicial del State
        this.estado = new EstadoCreada();

        calcularTotal();
    }

    public void setEstado(EstadoCompraState estado) {
        this.estado = estado;
    }

    public EstadoCompraState getEstado() {
        return estado;
    }

    public String getNombreEstado() {
        return estado.getNombreEstado();
    }

    public void addEntradaInterna(Entrada entrada) {
        if (entrada == null) {
            throw new IllegalArgumentException("La entrada no puede ser nula");
        }

        if (!entrada.tieneAsientoDisponible()) {
            throw new IllegalStateException("El asiento no esta disponible");
        }

        if (entrada.getAsiento() != null) {
            entrada.getAsiento().reservar();
        }

        entradas.add(entrada);
    }

    public void removeEntradaInterna(String idEntrada) {
        for (Entrada entrada : entradas) {
            if (entrada.getIdEntrada().equals(idEntrada) && entrada.getAsiento() != null) {
                entrada.getAsiento().liberar();
            }
        }

        entradas.removeIf(e -> e.getIdEntrada().equals(idEntrada));
    }

    public void addServicioInterno(ServicioAdicional servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio no puede ser nulo");
        }

        serviciosAdicionales.add(servicio);
    }

    public void removeServicioInterno(String idServicio) {
        serviciosAdicionales.removeIf(s -> s.getIdServicio().equals(idServicio));
    }

    public void agregarEntrada(Entrada entrada) {
        estado.agregarEntrada(this, entrada);
        calcularTotal();
    }

    public void eliminarEntrada(String idEntrada) {
        estado.eliminarEntrada(this, idEntrada);
        calcularTotal();
    }

    public void agregarServicioAdicional(ServicioAdicional servicio) {
        estado.agregarServicio(this, servicio);
        calcularTotal();
    }

    public void eliminarServicioAdicional(String idServicio) {
        estado.eliminarServicio(this, idServicio);
        calcularTotal();
    }

    public void cancelarCompra() {
        estado.cancelar(this);
        if ("CANCELADA".equals(getNombreEstado())) {
            anularEntradas();
        }
        notificarObservadores("Compra cancelada: " + idCompra);
    }

    public void confirmarCompra() {
        estado.confirmar(this);
        notificarObservadores("Compra confirmada: " + idCompra);
    }

    public void reembolsarCompra() {
        if (pago != null) {
            pago.reembolsarPago();
        }

        setEstado(new EstadoReembolsada());
        anularEntradas();
        notificarObservadores("Compra reembolsada: " + idCompra);
    }

    public void marcarIncidencia() {
        setEstado(new EstadoIncidencia());
        notificarObservadores("Incidencia registrada en la compra: " + idCompra);
    }

    public void registrarPago(Pago pago) {
        this.pago = pago;
        estado.registrarPago(this, pago);

        if (pago.getEstadoPago() == EstadoPago.APROBADO) {
            activarEntradas();
            notificarObservadores("Pago aprobado: " + idCompra);
        } else {
            notificarObservadores("Pago rechazado: " + idCompra);
        }
    }

    private void activarEntradas() {
        for (Entrada entrada : entradas) {
            entrada.activarEntrada();

            if (entrada.getAsiento() != null) {
                entrada.getAsiento().vender();
            }
        }
    }

    private void anularEntradas() {
        for (Entrada entrada : entradas) {
            entrada.anularEntrada();

            if (entrada.getAsiento() != null) {
                entrada.getAsiento().liberar();
            }
        }
    }

    public void calcularTotal() {

        total = 0;

        for (Entrada entrada : entradas) {
            total += entrada.getPrecioFinal();
        }

        for (ServicioAdicional servicio : serviciosAdicionales) {
            total += servicio.getPrecio();
        }
    }

    public String getIdCompra() {
        return idCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public double getTotal() {
        return total;
    }

    public List<Entrada> getEntradas() {
        return new ArrayList<>(entradas);
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return new ArrayList<>(serviciosAdicionales);
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public static class Builder {

        private String idCompra;
        private Usuario usuario;
        private Evento evento;
        private LocalDateTime fechaCreacion;

        private List<Entrada> entradas = new ArrayList<>();
        private List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();

        private Pago pago;

        public Builder(String idCompra) {
            this.idCompra = idCompra;
            this.fechaCreacion = LocalDateTime.now();
        }

        public Builder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder evento(Evento evento) {
            this.evento = evento;
            return this;
        }

        public Builder agregarEntrada(Entrada entrada) {
            this.entradas.add(entrada);
            return this;
        }

        public Builder agregarServicio(ServicioAdicional servicio) {
            this.serviciosAdicionales.add(servicio);
            return this;
        }

        public Builder pago(Pago pago) {
            this.pago = pago;
            return this;
        }

        public Compra build() {

            if (usuario == null || evento == null) {
                throw new IllegalStateException("Usuario y Evento son obligatorios");
            }

            return new Compra(this);
        }
    }
}
