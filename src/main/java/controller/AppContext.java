package controller;

import javafx.application.Platform;
import model.Administrador;
import model.Asiento;
import model.EstadoAsiento;
import model.EstadoEvento;
import model.Evento;
import model.Recinto;
import model.SistemaCompraFacade;
import model.SistemaEventos;
import model.TipoZona;
import model.Usuario;
import model.UsuarioComun;
import model.Zona;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda datos compartidos entre controladores.
 *
 * Mantiene el sistema principal, la fachada de compras y el usuario actual.
 */
public class AppContext {

    private static final AppContext INSTANCE = new AppContext();

    private final SistemaEventos sistema = SistemaEventos.getInstancia();
    private final SistemaCompraFacade comprasFacade = new SistemaCompraFacade();

    private Usuario usuarioActual;
    private Administrador administradorActual;
    private final List<Runnable> dataChangeListeners = new ArrayList<>();

    /**
     * Crea el contexto y carga datos iniciales de prueba.
     */
    private AppContext() {
        cargarDatosPrueba();
    }

    /**
     * Obtiene la instancia unica del contexto.
     *
     * @return contexto de la aplicacion
     */
    public static AppContext getInstance() {
        return INSTANCE;
    }

    public SistemaEventos getSistema() {
        return sistema;
    }

    public SistemaCompraFacade getComprasFacade() {
        return comprasFacade;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Administrador getAdministradorActual() {
        return administradorActual;
    }

    /**
     * Crea un identificador simple usando un prefijo y un consecutivo.
     *
     * @param prefix prefijo del identificador
     * @param size cantidad actual de elementos
     * @return identificador generado
     */
    public String nextId(String prefix, int size) {
        return prefix + "-" + String.format("%03d", size + 1);
    }

    /**
     * Registra una accion que se ejecuta cuando cambian los datos.
     *
     * @param listener accion a ejecutar
     */
    public void addDataChangeListener(Runnable listener) {
        if (listener != null && !dataChangeListeners.contains(listener)) {
            dataChangeListeners.add(listener);
        }
    }

    /**
     * Notifica a las pantallas que los datos cambiaron.
     */
    public void notifyDataChanged() {
        for (Runnable listener : new ArrayList<>(dataChangeListeners)) {
            // Si no estamos en el hilo de JavaFX, se agenda la actualizacion.
            if (Platform.isFxApplicationThread()) {
                listener.run();
            } else {
                Platform.runLater(listener);
            }
        }
    }

    /**
     * Carga datos iniciales para poder usar la aplicacion.
     */
    private void cargarDatosPrueba() {
        if (!sistema.getUsuarios().isEmpty()
                || !sistema.getEventos().isEmpty()
                || !sistema.getRecintos().isEmpty()) {
            return;
        }

        UsuarioComun usuario = new UsuarioComun(
                "U-001",
                "Ana Perez",
                "ana@mail.com",
                "3001234567",
                "1234"
        );
        usuario.agregarMetodoPago("NEQUI");
        usuario.agregarMetodoPago("PSE");
        sistema.registrarUsuario(usuario);
        usuarioActual = usuario;

        administradorActual = new Administrador(
                "A-001",
                "Admin Operaciones",
                "admin@mail.com",
                "3010000000",
                "admin"
        );
        sistema.registrarAdministrador(administradorActual);

        Recinto coliseo = new Recinto("R-001", "Coliseo Central", "Av. 10 # 20-30", "Bogota");
        Zona vip = new Zona("Z-001", "VIP", 80, 180000, TipoZona.VIP);
        Zona preferencial = new Zona("Z-002", "Preferencial", 120, 120000, TipoZona.PREFERENCIAL);
        Zona general = new Zona("Z-003", "General", 300, 70000, TipoZona.GENERAL);
        agregarAsientos(vip, "A", 12);
        agregarAsientos(preferencial, "B", 16);
        agregarAsientos(general, "C", 20);
        coliseo.agregarZona(vip);
        coliseo.agregarZona(preferencial);
        coliseo.agregarZona(general);
        sistema.agregarRecinto(coliseo);

        Recinto teatro = new Recinto("R-002", "Teatro Municipal", "Calle 45 # 12-10", "Medellin");
        Zona platea = new Zona("Z-004", "Platea", 100, 95000, TipoZona.PREFERENCIAL);
        Zona balcon = new Zona("Z-005", "Balcon", 160, 55000, TipoZona.GENERAL);
        agregarAsientos(platea, "P", 10);
        agregarAsientos(balcon, "D", 14);
        teatro.agregarZona(platea);
        teatro.agregarZona(balcon);
        sistema.agregarRecinto(teatro);

        sistema.agregarEvento(new Evento(
                "E-001",
                "Rock al Parque",
                "Concierto",
                "Festival musical con bandas nacionales.",
                "Bogota",
                LocalDateTime.now().plusDays(15).withHour(19).withMinute(0),
                EstadoEvento.PUBLICADO,
                coliseo
        ));
        sistema.agregarEvento(new Evento(
                "E-002",
                "Hamlet",
                "Teatro",
                "Obra clasica en funcion nocturna.",
                "Medellin",
                LocalDateTime.now().plusDays(8).withHour(20).withMinute(0),
                EstadoEvento.PUBLICADO,
                teatro
        ));
        sistema.agregarEvento(new Evento(
                "E-003",
                "Java Summit",
                "Conferencia",
                "Charlas de arquitectura, patrones y JavaFX.",
                "Bogota",
                LocalDateTime.now().plusDays(30).withHour(9).withMinute(0),
                EstadoEvento.BORRADOR,
                coliseo
        ));
    }

    /**
     * Agrega asientos consecutivos a una zona.
     *
     * @param zona zona que recibe los asientos
     * @param fila fila usada para los asientos
     * @param cantidad cantidad de asientos
     */
    private void agregarAsientos(Zona zona, String fila, int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            zona.agregarAsiento(new Asiento(
                    zona.getIdZona() + "-S" + i,
                    fila,
                    i,
                    EstadoAsiento.DISPONIBLE
            ));
        }
    }
}

