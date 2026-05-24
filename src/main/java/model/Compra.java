package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una compra hecha por un usuario para un evento.
 *
 * La compra guarda entradas, servicios adicionales, pago y estado.
 */
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

    /**
     * Agrega un observador a la compra.
     *
     * @param obs observador que recibira notificaciones
     */
    @Override
    public void agregarObservador(IObservador obs) {
        observadores.add(obs);
    }

    /**
     * Elimina un observador de la compra.
     *
     * @param obs observador que se desea eliminar
     */
    @Override
    public void eliminarObservador(IObservador obs) {
        observadores.remove(obs);
    }

    /**
     * Notifica un mensaje a todos los observadores.
     *
     * @param mensaje mensaje que se enviara
     */
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

        // Toda compra inicia en estado creada.
        this.estado = new EstadoCreada();

        calcularTotal();
    }

    /**
     * Cambia el estado actual de la compra.
     *
     * @param estado nuevo estado de la compra
     */
    public void setEstado(EstadoCompraState estado) {
        this.estado = estado;
    }

    public EstadoCompraState getEstado() {
        return estado;
    }

    public String getNombreEstado() {
        return estado.getNombreEstado();
    }

    /**
     * Agrega una entrada sin pasar por el estado de la compra.
     *
     * @param entrada entrada que se desea agregar
     */
    public void addEntradaInterna(Entrada entrada) {
        if (entrada == null) {
            throw new IllegalArgumentException("La entrada no puede ser nula");
        }

        if (!entrada.tieneAsientoDisponible()) {
            throw new IllegalStateException("El asiento no esta disponible");
        }

        if (entrada.getAsiento() != null) {
            // Al agregar la entrada, el asiento queda reservado.
            entrada.getAsiento().reservar();
        }

        entradas.add(entrada);
    }

    /**
     * Elimina una entrada por su identificador.
     *
     * @param idEntrada identificador de la entrada
     */
    public void removeEntradaInterna(String idEntrada) {
        for (Entrada entrada : entradas) {
            if (entrada.getIdEntrada().equals(idEntrada) && entrada.getAsiento() != null) {
                entrada.getAsiento().liberar();
            }
        }

        entradas.removeIf(e -> e.getIdEntrada().equals(idEntrada));
    }

    /**
     * Agrega un servicio adicional sin pasar por el estado.
     *
     * @param servicio servicio que se desea agregar
     */
    public void addServicioInterno(ServicioAdicional servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio no puede ser nulo");
        }

        serviciosAdicionales.add(servicio);
    }

    /**
     * Elimina un servicio adicional por su identificador.
     *
     * @param idServicio identificador del servicio
     */
    public void removeServicioInterno(String idServicio) {
        serviciosAdicionales.removeIf(s -> s.getIdServicio().equals(idServicio));
    }

    /**
     * Agrega una entrada respetando el estado actual de la compra.
     *
     * @param entrada entrada que se desea agregar
     */
    public void agregarEntrada(Entrada entrada) {
        // El estado decide si esta operacion se permite.
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

    /**
     * Cancela la compra y anula sus entradas.
     */
    public void cancelarCompra() {
        String estadoAnterior = getNombreEstado();

        estado.cancelar(this);

        if (!"CANCELADA".equals(getNombreEstado())) {
            throw new IllegalStateException("No se puede cancelar una compra en estado " + estadoAnterior + ".");
        }

        // Si la cancelacion fue valida, se anulan entradas y se avisa.
        anularEntradas();
        notificarObservadores("Compra cancelada: " + idCompra);
    }

    /**
     * Confirma la compra segun su estado actual.
     */
    public void confirmarCompra() {
        String estadoAnterior = getNombreEstado();

        if ("CONFIRMADA".equals(estadoAnterior)) {
            throw new IllegalStateException("No se puede confirmar una compra en estado " + estadoAnterior + ".");
        }

        estado.confirmar(this);

        if (!"CONFIRMADA".equals(getNombreEstado())) {
            throw new IllegalStateException("No se puede confirmar una compra en estado " + estadoAnterior + ".");
        }

        notificarObservadores("Compra confirmada: " + idCompra);
    }

    /**
     * Reembolsa la compra y anula sus entradas.
     */
    public void reembolsarCompra() {
        if ("CANCELADA".equals(getNombreEstado())) {
            throw new IllegalStateException("No se puede reembolsar una compra cancelada.");
        }

        if (pago != null) {
            pago.reembolsarPago();
        }

        setEstado(new EstadoReembolsada());
        anularEntradas();
        notificarObservadores("Compra reembolsada: " + idCompra);
    }

    /**
     * Marca la compra con una incidencia.
     */
    public void marcarIncidencia() {
        setEstado(new EstadoIncidencia());
        notificarObservadores("Incidencia registrada en la compra: " + idCompra);
    }

    /**
     * Registra el pago de la compra.
     *
     * @param pago pago asociado a la compra
     */
    public void registrarPago(Pago pago) {
        this.pago = pago;
        estado.registrarPago(this, pago);

        if (pago.getEstadoPago() == EstadoPago.APROBADO) {
            // Un pago aprobado activa entradas y vende asientos.
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

    /**
     * Calcula el total sumando entradas y servicios.
     */
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

    /**
     * Ayuda a crear una compra paso a paso.
     */
    public static class Builder {

        private String idCompra;
        private Usuario usuario;
        private Evento evento;
        private LocalDateTime fechaCreacion;

        private List<Entrada> entradas = new ArrayList<>();
        private List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();

        private Pago pago;

        /**
         * Crea un constructor de compra.
         *
         * @param idCompra identificador de la compra
         */
        public Builder(String idCompra) {
            this.idCompra = idCompra;
            this.fechaCreacion = LocalDateTime.now();
        }

        /**
         * Define el usuario de la compra.
         *
         * @param usuario usuario comprador
         * @return builder actual
         */
        public Builder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        /**
         * Define el evento de la compra.
         *
         * @param evento evento comprado
         * @return builder actual
         */
        public Builder evento(Evento evento) {
            this.evento = evento;
            return this;
        }

        /**
         * Agrega una entrada a la compra en construccion.
         *
         * @param entrada entrada que se agrega
         * @return builder actual
         */
        public Builder agregarEntrada(Entrada entrada) {
            this.entradas.add(entrada);
            return this;
        }

        /**
         * Agrega un servicio a la compra en construccion.
         *
         * @param servicio servicio que se agrega
         * @return builder actual
         */
        public Builder agregarServicio(ServicioAdicional servicio) {
            this.serviciosAdicionales.add(servicio);
            return this;
        }

        /**
         * Define el pago de la compra.
         *
         * @param pago pago de la compra
         * @return builder actual
         */
        public Builder pago(Pago pago) {
            this.pago = pago;
            return this;
        }

        /**
         * Construye la compra.
         *
         * @return compra creada
         */
        public Compra build() {

            if (usuario == null || evento == null) {
                throw new IllegalStateException("Usuario y Evento son obligatorios");
            }

            return new Compra(this);
        }
    }
}
