package model;

import java.util.ArrayList;
import java.util.List;

public class SistemaEventos {

    private static SistemaEventos instancia;

    private List<Usuario> usuarios;

    private List<Administrador> administradores;

    private List<Evento> eventos;

    private List<Recinto> recintos;

    private List<Compra> compras;

    private List<Incidencia> incidencias;

    private List<Pago> pagos;

    private SistemaEventos() {

        this.usuarios = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.recintos = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.incidencias = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    public static SistemaEventos getInstancia() {

        if (instancia == null) {
            instancia = new SistemaEventos();
        }

        return instancia;
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean eliminarUsuario(String idUsuario) {
        return usuarios.removeIf(usuario -> usuario.getIdUsuario().equals(idUsuario));
    }

    public Usuario obtenerUsuarioPorId(String idUsuario) {

        for (Usuario usuario : usuarios) {

            if (usuario.getIdUsuario().equals(idUsuario)) {
                return usuario;
            }
        }

        return null;
    }

    public boolean existeUsuarioPorId(String idUsuario) {
        return obtenerUsuarioPorId(idUsuario) != null;
    }


    public void registrarAdministrador(Administrador administrador) {
        administradores.add(administrador);
    }

    public Administrador obtenerAdministradorPorId(String idAdministrador) {

        for (Administrador administrador : administradores) {

            if (administrador.getIdUsuario().equals(idAdministrador)) {
                return administrador;
            }
        }

        return null;
    }

    public boolean existeAdministradorPorId(String idAdministrador) {
        return obtenerAdministradorPorId(idAdministrador) != null;
    }

    public void agregarEvento(Evento evento) {
        eventos.add(evento);
    }

    public boolean eliminarEvento(String idEvento) {
        return eventos.removeIf(evento -> evento.getIdEvento().equals(idEvento));
    }

    public Evento obtenerEventoPorId(String idEvento) {

        for (Evento evento : eventos) {

            if (evento.getIdEvento().equals(idEvento)) {
                return evento;
            }
        }

        return null;
    }

    public boolean existeEventoPorId(String idEvento) {
        return obtenerEventoPorId(idEvento) != null;
    }

    public void agregarRecinto(Recinto recinto) {
        recintos.add(recinto);
    }

    public boolean eliminarRecinto(String idRecinto) {
        return recintos.removeIf(recinto -> recinto.getIdRecinto().equals(idRecinto));
    }

    public Recinto obtenerRecintoPorId(String idRecinto) {

        for (Recinto recinto : recintos) {

            if (recinto.getIdRecinto().equals(idRecinto)) {
                return recinto;
            }
        }

        return null;
    }

    public boolean existeRecintoPorId(String idRecinto) {
        return obtenerRecintoPorId(idRecinto) != null;
    }

    public void agregarCompra(Compra compra) {
        compras.add(compra);
    }

    public Compra obtenerCompraPorId(String idCompra) {

        for (Compra compra : compras) {

            if (compra.getIdCompra().equals(idCompra)) {
                return compra;
            }
        }

        return null;
    }

    public boolean existeCompraPorId(String idCompra) {
        return obtenerCompraPorId(idCompra) != null;
    }


    public void registrarPago(Pago pago) {
        pagos.add(pago);
    }

    public Pago obtenerPagoPorId(String idPago) {

        for (Pago pago : pagos) {

            if (pago.getIdPago().equals(idPago)) {
                return pago;
            }
        }

        return null;
    }


    public void registrarIncidencia(Incidencia incidencia) {
        incidencias.add(incidencia);
    }

    public Incidencia obtenerIncidenciaPorId(String idIncidencia) {

        for (Incidencia incidencia : incidencias) {

            if (incidencia.getIdIncidencia().equals(idIncidencia)) {
                return incidencia;
            }
        }

        return null;
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public List<Administrador> getAdministradores() {
        return new ArrayList<>(administradores);
    }

    public List<Evento> getEventos() {
        return new ArrayList<>(eventos);
    }

    public List<Recinto> getRecintos() {
        return new ArrayList<>(recintos);
    }

    public List<Compra> getCompras() {
        return new ArrayList<>(compras);
    }

    public List<Incidencia> getIncidencias() {
        return new ArrayList<>(incidencias);
    }

    public List<Pago> getPagos() {
        return new ArrayList<>(pagos);
    }
}
