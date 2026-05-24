package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import model.Administrador;
import model.Asiento;
import model.Compra;
import model.EstadoAsiento;
import model.EstadoEvento;
import model.Evento;
import model.Incidencia;
import model.Recinto;
import model.ServicioAdicional;
import model.ServicioMetricas;
import model.SistemaEventos;
import model.TipoZona;
import model.Usuario;
import model.UsuarioComun;
import model.Zona;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de la pantalla de administracion.
 *
 * Permite gestionar usuarios, recintos, zonas, asientos, eventos,
 * compras, incidencias y reportes.
 */
public class AdminController {

    private final AppContext context = AppContext.getInstance();
    private final SistemaEventos sistema = context.getSistema();
    private final ServicioMetricas metricas = new ServicioMetricas();

    @FXML private Label lblAdminMensaje;
    @FXML private Label lblMetricasResumen;

    @FXML private TextField txtAdminUsuarioNombre;
    @FXML private TextField txtAdminUsuarioCorreo;
    @FXML private TextField txtAdminUsuarioTelefono;
    @FXML private TableView<Usuario> tablaAdminUsuarios;
    @FXML private TableColumn<Usuario, String> colAdminUsuarioId;
    @FXML private TableColumn<Usuario, String> colAdminUsuarioNombre;
    @FXML private TableColumn<Usuario, String> colAdminUsuarioCorreo;
    @FXML private TableColumn<Usuario, String> colAdminUsuarioTelefono;

    @FXML private TextField txtRecintoNombre;
    @FXML private TextField txtRecintoDireccion;
    @FXML private TextField txtRecintoCiudad;
    @FXML private TableView<Recinto> tablaRecintos;
    @FXML private TableColumn<Recinto, String> colRecintoId;
    @FXML private TableColumn<Recinto, String> colRecintoNombre;
    @FXML private TableColumn<Recinto, String> colRecintoCiudad;
    @FXML private TableColumn<Recinto, Number> colRecintoCapacidad;

    @FXML private ComboBox<Recinto> comboRecintoZona;
    @FXML private TextField txtZonaNombre;
    @FXML private TextField txtZonaCapacidad;
    @FXML private TextField txtZonaPrecio;
    @FXML private ComboBox<TipoZona> comboTipoZona;
    @FXML private TableView<Zona> tablaZonas;
    @FXML private TableColumn<Zona, String> colZonaId;
    @FXML private TableColumn<Zona, String> colZonaNombre;
    @FXML private TableColumn<Zona, Number> colZonaCapacidad;
    @FXML private TableColumn<Zona, Number> colZonaPrecio;
    @FXML private TableColumn<Zona, Number> colZonaOcupacion;

    @FXML private ComboBox<Zona> comboZonaAsiento;
    @FXML private TextField txtAsientoFila;
    @FXML private TextField txtAsientoNumero;
    @FXML private ComboBox<EstadoAsiento> comboEstadoAsiento;
    @FXML private TableView<Asiento> tablaAsientos;
    @FXML private TableColumn<Asiento, String> colAsientoId;
    @FXML private TableColumn<Asiento, String> colAsientoFila;
    @FXML private TableColumn<Asiento, Number> colAsientoNumero;
    @FXML private TableColumn<Asiento, String> colAsientoEstado;

    @FXML private TextField txtEventoNombre;
    @FXML private TextField txtEventoCategoria;
    @FXML private TextArea txtEventoDescripcion;
    @FXML private TextField txtEventoCiudad;
    @FXML private DatePicker dpEventoFecha;
    @FXML private ComboBox<Recinto> comboEventoRecinto;
    @FXML private ComboBox<EstadoEvento> comboEventoEstado;
    @FXML private TableView<Evento> tablaAdminEventos;
    @FXML private TableColumn<Evento, String> colAdminEventoId;
    @FXML private TableColumn<Evento, String> colAdminEventoNombre;
    @FXML private TableColumn<Evento, String> colAdminEventoEstado;
    @FXML private TableColumn<Evento, String> colAdminEventoRecinto;

    @FXML private TableView<Compra> tablaAdminCompras;
    @FXML private TableColumn<Compra, String> colAdminCompraId;
    @FXML private TableColumn<Compra, String> colAdminCompraUsuario;
    @FXML private TableColumn<Compra, String> colAdminCompraEvento;
    @FXML private TableColumn<Compra, String> colAdminCompraEstado;
    @FXML private TableColumn<Compra, Number> colAdminCompraTotal;

    @FXML private TextField txtIncidenciaTipo;
    @FXML private TextField txtIncidenciaEntidad;
    @FXML private TextArea txtIncidenciaDescripcion;
    @FXML private TableView<Incidencia> tablaIncidencias;
    @FXML private TableColumn<Incidencia, String> colIncidenciaId;
    @FXML private TableColumn<Incidencia, String> colIncidenciaTipo;
    @FXML private TableColumn<Incidencia, String> colIncidenciaEntidad;
    @FXML private TableColumn<Incidencia, String> colIncidenciaFecha;

    @FXML private Label lblTotalVentas;
    @FXML private Label lblVentasRealizadas;
    @FXML private Label lblRangoVentas;
    @FXML private TextArea txtOcupacionZona;
    @FXML private BarChart<String, Number> chartServiciosAdicionales;
    @FXML private BarChart<String, Number> chartTopEventos;
    @FXML private DatePicker dpReporteInicio;
    @FXML private DatePicker dpReporteFin;

    @FXML
    /**
     * Inicializa combos, tablas y datos de la pantalla.
     */
    private void initialize() {
        configurarCombos();
        configurarTablas();
        context.addDataChangeListener(this::refrescarTodo);
        refrescarTodo();
    }

    @FXML
    private void crearUsuarioAdmin() {
        if (estaVacio(txtAdminUsuarioNombre.getText()) || estaVacio(txtAdminUsuarioCorreo.getText())) {
            mensaje("Nombre y correo de usuario son obligatorios.");
            return;
        }
        Usuario usuario = new UsuarioComun(
                context.nextId("U", sistema.getUsuarios().size()),
                txtAdminUsuarioNombre.getText().trim(),
                txtAdminUsuarioCorreo.getText().trim(),
                txtAdminUsuarioTelefono.getText().trim(),
                "1234"
        );
        sistema.registrarUsuario(usuario);
        txtAdminUsuarioNombre.clear();
        txtAdminUsuarioCorreo.clear();
        txtAdminUsuarioTelefono.clear();
        context.notifyDataChanged();
        mensaje("Usuario creado.");
    }

    @FXML
    private void actualizarUsuarioAdmin() {
        Usuario usuario = tablaAdminUsuarios.getSelectionModel().getSelectedItem();
        if (usuario == null) {
            mensaje("Selecciona un usuario de la tabla.");
            return;
        }
        if (estaVacio(txtAdminUsuarioNombre.getText()) || estaVacio(txtAdminUsuarioCorreo.getText())) {
            mensaje("Nombre y correo de usuario son obligatorios.");
            return;
        }
        usuario.actualizarPerfil(
                txtAdminUsuarioNombre.getText().trim(),
                txtAdminUsuarioCorreo.getText().trim(),
                txtAdminUsuarioTelefono.getText().trim()
        );
        context.notifyDataChanged();
        mensaje("Usuario actualizado.");
    }

    @FXML
    private void eliminarUsuarioAdmin() {
        Usuario usuario = tablaAdminUsuarios.getSelectionModel().getSelectedItem();
        if (usuario == null) {
            mensaje("Selecciona un usuario de la tabla.");
            return;
        }
        sistema.eliminarUsuario(usuario.getIdUsuario());
        if (usuario == context.getUsuarioActual()) {
            context.setUsuarioActual(null);
        }
        context.notifyDataChanged();
        mensaje("Usuario eliminado.");
    }

    @FXML
    private void crearRecinto() {
        if (estaVacio(txtRecintoNombre.getText())) {
            mensaje("El nombre del recinto es obligatorio.");
            return;
        }
        Recinto recinto = new Recinto(
                context.nextId("R", sistema.getRecintos().size()),
                txtRecintoNombre.getText().trim(),
                txtRecintoDireccion.getText().trim(),
                txtRecintoCiudad.getText().trim()
        );
        sistema.agregarRecinto(recinto);
        txtRecintoNombre.clear();
        txtRecintoDireccion.clear();
        txtRecintoCiudad.clear();
        context.notifyDataChanged();
        mensaje("Recinto creado.");
    }

    @FXML
    private void actualizarRecinto() {
        Recinto recinto = tablaRecintos.getSelectionModel().getSelectedItem();
        if (recinto == null) {
            mensaje("Selecciona un recinto de la tabla.");
            return;
        }
        if (estaVacio(txtRecintoNombre.getText())) {
            mensaje("El nombre del recinto es obligatorio.");
            return;
        }
        recinto.setNombre(txtRecintoNombre.getText().trim());
        recinto.setDireccion(txtRecintoDireccion.getText().trim());
        recinto.setCiudad(txtRecintoCiudad.getText().trim());
        context.notifyDataChanged();
        mensaje("Recinto actualizado.");
    }

    @FXML
    private void eliminarRecinto() {
        Recinto recinto = tablaRecintos.getSelectionModel().getSelectedItem();
        if (recinto == null) {
            mensaje("Selecciona un recinto de la tabla.");
            return;
        }
        sistema.eliminarRecinto(recinto.getIdRecinto());
        context.notifyDataChanged();
        mensaje("Recinto eliminado.");
    }

    @FXML
    private void crearZona() {
        Recinto recinto = comboRecintoZona.getValue();
        if (recinto == null || estaVacio(txtZonaNombre.getText())) {
            mensaje("Selecciona recinto y escribe la zona.");
            return;
        }
        try {
            Zona zona = new Zona(
                    context.nextId("Z", totalZonas()),
                    txtZonaNombre.getText().trim(),
                    Integer.parseInt(txtZonaCapacidad.getText().trim()),
                    Double.parseDouble(txtZonaPrecio.getText().trim()),
                    comboTipoZona.getValue()
            );
            recinto.agregarZona(zona);
            txtZonaNombre.clear();
            txtZonaCapacidad.clear();
            txtZonaPrecio.clear();
            context.notifyDataChanged();
            comboRecintoZona.getSelectionModel().select(recinto);
            mensaje("Zona creada.");
        } catch (NumberFormatException ex) {
            mensaje("Capacidad y precio deben ser numericos.");
        }
    }

    @FXML
    private void actualizarZona() {
        Zona zona = tablaZonas.getSelectionModel().getSelectedItem();
        if (zona == null) {
            mensaje("Selecciona una zona de la tabla.");
            return;
        }
        if (estaVacio(txtZonaNombre.getText())) {
            mensaje("El nombre de la zona es obligatorio.");
            return;
        }
        try {
            zona.setNombre(txtZonaNombre.getText().trim());
            zona.setCapacidad(Integer.parseInt(txtZonaCapacidad.getText().trim()));
            zona.setPrecioBase(Double.parseDouble(txtZonaPrecio.getText().trim()));
            zona.setTipoZona(comboTipoZona.getValue());
            context.notifyDataChanged();
            mensaje("Zona actualizada.");
        } catch (NumberFormatException ex) {
            mensaje("Capacidad y precio deben ser numericos.");
        }
    }

    @FXML
    private void eliminarZona() {
        Zona zona = tablaZonas.getSelectionModel().getSelectedItem();
        if (zona == null) {
            mensaje("Selecciona una zona de la tabla.");
            return;
        }
        Recinto recinto = buscarRecintoDeZona(zona);
        if (recinto == null) {
            mensaje("No se encontro el recinto de la zona seleccionada.");
            return;
        }
        recinto.eliminarZona(zona.getIdZona());
        context.notifyDataChanged();
        mensaje("Zona eliminada.");
    }

    @FXML
    private void crearAsiento() {
        Zona zona = comboZonaAsiento.getValue();
        if (zona == null || estaVacio(txtAsientoFila.getText())) {
            mensaje("Selecciona zona y escribe la fila.");
            return;
        }
        try {
            Asiento asiento = new Asiento(
                    zona.getIdZona() + "-S" + (zona.getAsientos().size() + 1),
                    txtAsientoFila.getText().trim(),
                    Integer.parseInt(txtAsientoNumero.getText().trim()),
                    comboEstadoAsiento.getValue()
            );
            zona.agregarAsiento(asiento);
            txtAsientoFila.clear();
            txtAsientoNumero.clear();
            context.notifyDataChanged();
            mensaje("Asiento creado.");
        } catch (NumberFormatException ex) {
            mensaje("El numero de asiento debe ser numerico.");
        }
    }

    @FXML
    private void actualizarAsiento() {
        Asiento asiento = tablaAsientos.getSelectionModel().getSelectedItem();
        if (asiento == null) {
            mensaje("Selecciona un asiento de la tabla.");
            return;
        }
        if (estaVacio(txtAsientoFila.getText())) {
            mensaje("La fila del asiento es obligatoria.");
            return;
        }
        try {
            asiento.setFila(txtAsientoFila.getText().trim());
            asiento.setNumero(Integer.parseInt(txtAsientoNumero.getText().trim()));
            asiento.cambiarEstado(comboEstadoAsiento.getValue());
            context.notifyDataChanged();
            mensaje("Asiento actualizado.");
        } catch (NumberFormatException ex) {
            mensaje("El numero de asiento debe ser numerico.");
        }
    }

    @FXML
    private void cambiarEstadoAsiento() {
        Asiento asiento = tablaAsientos.getSelectionModel().getSelectedItem();
        if (asiento == null) {
            mensaje("Selecciona un asiento de la tabla.");
            return;
        }
        asiento.cambiarEstado(comboEstadoAsiento.getValue());
        context.notifyDataChanged();
        mensaje("Estado de asiento actualizado.");
    }

    @FXML
    private void eliminarAsiento() {
        Asiento asiento = tablaAsientos.getSelectionModel().getSelectedItem();
        if (asiento == null) {
            mensaje("Selecciona un asiento de la tabla.");
            return;
        }
        Zona zona = buscarZonaDeAsiento(asiento);
        if (zona == null) {
            mensaje("No se encontro la zona del asiento seleccionado.");
            return;
        }
        zona.eliminarAsiento(asiento.getIdAsiento());
        context.notifyDataChanged();
        mensaje("Asiento eliminado.");
    }

    @FXML
    private void crearEvento() {
        Recinto recinto = comboEventoRecinto.getValue();
        LocalDate fecha = dpEventoFecha.getValue();
        if (recinto == null || fecha == null || estaVacio(txtEventoNombre.getText())) {
            mensaje("Nombre, fecha y recinto son obligatorios.");
            return;
        }
        Evento evento = new Evento(
                context.nextId("E", sistema.getEventos().size()),
                txtEventoNombre.getText().trim(),
                txtEventoCategoria.getText().trim(),
                txtEventoDescripcion.getText().trim(),
                txtEventoCiudad.getText().trim(),
                LocalDateTime.of(fecha, java.time.LocalTime.of(19, 0)),
                EstadoEvento.PUBLICADO,
                recinto
        );
        sistema.agregarEvento(evento);
        txtEventoNombre.clear();
        txtEventoCategoria.clear();
        txtEventoDescripcion.clear();
        txtEventoCiudad.clear();
        dpEventoFecha.setValue(null);
        comboEventoEstado.getSelectionModel().select(EstadoEvento.PUBLICADO);
        comboEventoEstado.setDisable(true);
        context.notifyDataChanged();
        mensaje("Evento creado con estado PUBLICADO.");
    }

    @FXML
    private void actualizarEvento() {
        Evento evento = tablaAdminEventos.getSelectionModel().getSelectedItem();
        Recinto recinto = comboEventoRecinto.getValue();
        LocalDate fecha = dpEventoFecha.getValue();
        if (evento == null) {
            mensaje("Selecciona un evento de la tabla.");
            return;
        }
        if (recinto == null || fecha == null || estaVacio(txtEventoNombre.getText())) {
            mensaje("Nombre, fecha y recinto son obligatorios.");
            return;
        }
        evento.setNombre(txtEventoNombre.getText().trim());
        evento.setCategoria(txtEventoCategoria.getText().trim());
        evento.setDescripcion(txtEventoDescripcion.getText().trim());
        evento.setCiudad(txtEventoCiudad.getText().trim());
        evento.setFechaHora(LocalDateTime.of(fecha, evento.getFechaHora().toLocalTime()));
        evento.setRecinto(recinto);
        evento.setEstadoEvento(comboEventoEstado.getValue());
        context.notifyDataChanged();
        mensaje("Evento actualizado.");
    }

    @FXML
    private void eliminarEvento() {
        Evento evento = tablaAdminEventos.getSelectionModel().getSelectedItem();
        if (evento == null) {
            mensaje("Selecciona un evento de la tabla.");
            return;
        }
        sistema.eliminarEvento(evento.getIdEvento());
        context.notifyDataChanged();
        mensaje("Evento eliminado.");
    }

    @FXML
    private void actualizarEstadoEvento() {
        Evento evento = tablaAdminEventos.getSelectionModel().getSelectedItem();
        if (evento == null) {
            mensaje("Selecciona un evento de la tabla.");
            return;
        }
        evento.setEstadoEvento(comboEventoEstado.getValue());
        context.notifyDataChanged();
        mensaje("Estado de evento actualizado.");
    }

    @FXML
    private void confirmarCompraAdmin() {
        Compra compra = tablaAdminCompras.getSelectionModel().getSelectedItem();
        if (compra == null) {
            mensaje("Selecciona una compra de la tabla.");
            return;
        }
        try {
            compra.confirmarCompra();
            context.notifyDataChanged();
            mensaje("Compra confirmada.");
        } catch (IllegalStateException ex) {
            mensaje(ex.getMessage());
        }
    }

    @FXML
    private void cancelarCompraAdmin() {
        Compra compra = tablaAdminCompras.getSelectionModel().getSelectedItem();
        if (compra == null) {
            mensaje("Selecciona una compra de la tabla.");
            return;
        }
        Administrador admin = context.getAdministradorActual();
        try {
            admin.cancelarCompra(compra);
            context.notifyDataChanged();
            mensaje("Compra cancelada.");
        } catch (IllegalStateException ex) {
            mensaje(ex.getMessage());
        }
    }

    @FXML
    private void reembolsarCompraAdmin() {
        Compra compra = tablaAdminCompras.getSelectionModel().getSelectedItem();
        if (compra == null) {
            mensaje("Selecciona una compra de la tabla.");
            return;
        }
        try {
            compra.reembolsarCompra();
            context.notifyDataChanged();
            mensaje("Compra reembolsada.");
        } catch (IllegalStateException ex) {
            mensaje(ex.getMessage());
        }
    }

    @FXML
    private void registrarIncidencia() {
        if (estaVacio(txtIncidenciaTipo.getText()) || estaVacio(txtIncidenciaEntidad.getText())) {
            mensaje("Tipo y entidad son obligatorios.");
            return;
        }
        Incidencia incidencia = new Incidencia(
                context.nextId("I", sistema.getIncidencias().size()),
                txtIncidenciaTipo.getText().trim(),
                txtIncidenciaDescripcion.getText().trim(),
                LocalDateTime.now(),
                txtIncidenciaEntidad.getText().trim()
        );
        context.getAdministradorActual().registrarIncidencia(incidencia);
        txtIncidenciaTipo.clear();
        txtIncidenciaDescripcion.clear();
        txtIncidenciaEntidad.clear();
        context.notifyDataChanged();
        mensaje("Incidencia registrada.");
    }

    @FXML
    /**
     * Recarga toda la informacion mostrada en la vista.
     */
    private void refrescarTodo() {
        tablaAdminUsuarios.setItems(FXCollections.observableArrayList(sistema.getUsuarios()));
        tablaRecintos.setItems(FXCollections.observableArrayList(sistema.getRecintos()));
        tablaAdminEventos.setItems(FXCollections.observableArrayList(sistema.getEventos()));
        tablaAdminCompras.setItems(FXCollections.observableArrayList(sistema.getCompras()));
        tablaIncidencias.setItems(FXCollections.observableArrayList(sistema.getIncidencias()));

        comboRecintoZona.setItems(FXCollections.observableArrayList(sistema.getRecintos()));
        comboEventoRecinto.setItems(FXCollections.observableArrayList(sistema.getRecintos()));
        if (!comboRecintoZona.getItems().isEmpty() && comboRecintoZona.getValue() == null) {
            comboRecintoZona.getSelectionModel().selectFirst();
        }
        if (!comboEventoRecinto.getItems().isEmpty() && comboEventoRecinto.getValue() == null) {
            comboEventoRecinto.getSelectionModel().selectFirst();
        }
        refrescarZonas();
        refrescarAsientos();
        refrescarMetricas();
        tablaAdminUsuarios.refresh();
        tablaRecintos.refresh();
        tablaZonas.refresh();
        tablaAsientos.refresh();
        tablaAdminEventos.refresh();
        tablaAdminCompras.refresh();
        tablaIncidencias.refresh();
    }

    /**
     * Configura listas desplegables y sus eventos de cambio.
     */
    private void configurarCombos() {
        comboTipoZona.setItems(FXCollections.observableArrayList(TipoZona.values()));
        comboTipoZona.getSelectionModel().select(TipoZona.GENERAL);

        comboEstadoAsiento.setItems(FXCollections.observableArrayList(EstadoAsiento.values()));
        comboEstadoAsiento.getSelectionModel().select(EstadoAsiento.DISPONIBLE);

        comboEventoEstado.setItems(FXCollections.observableArrayList(EstadoEvento.values()));
        comboEventoEstado.getSelectionModel().select(EstadoEvento.PUBLICADO);
        comboEventoEstado.setDisable(true);

        dpReporteInicio.valueProperty().addListener((obs, oldValue, newValue) -> refrescarMetricas());
        dpReporteFin.valueProperty().addListener((obs, oldValue, newValue) -> refrescarMetricas());

        StringConverter<Recinto> recintoConverter = new StringConverter<Recinto>() {
            @Override
            public String toString(Recinto recinto) {
                return recinto == null ? "" : recinto.getIdRecinto() + " - " + recinto.getNombre();
            }

            @Override
            public Recinto fromString(String value) {
                return null;
            }
        };
        comboRecintoZona.setConverter(recintoConverter);
        comboEventoRecinto.setConverter(recintoConverter);
        comboRecintoZona.valueProperty().addListener((obs, oldValue, newValue) -> refrescarZonas());

        comboZonaAsiento.setConverter(new StringConverter<Zona>() {
            @Override
            public String toString(Zona zona) {
                return zona == null ? "" : zona.getIdZona() + " - " + zona.getNombre();
            }

            @Override
            public Zona fromString(String value) {
                return null;
            }
        });
        comboZonaAsiento.valueProperty().addListener((obs, oldValue, newValue) -> refrescarAsientos());
    }

    /**
     * Configura las columnas y seleccion de las tablas.
     */
    private void configurarTablas() {
        colAdminUsuarioId.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getIdUsuario()));
        colAdminUsuarioNombre.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombreCompleto()));
        colAdminUsuarioCorreo.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCorreoElectronico()));
        colAdminUsuarioTelefono.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNumeroTelefono()));

        colRecintoId.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getIdRecinto()));
        colRecintoNombre.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombre()));
        colRecintoCiudad.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCiudad()));
        colRecintoCapacidad.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().obtenerCapacidadTotal()));

        colZonaId.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getIdZona()));
        colZonaNombre.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombre()));
        colZonaCapacidad.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCapacidad()));
        colZonaPrecio.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPrecioBase()));
        colZonaOcupacion.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().consultarOcupacion()));

        colAsientoId.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getIdAsiento()));
        colAsientoFila.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFila()));
        colAsientoNumero.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getNumero()));
        colAsientoEstado.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEstadoAsiento().name()));

        colAdminEventoId.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getIdEvento()));
        colAdminEventoNombre.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombre()));
        colAdminEventoEstado.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEstadoEvento().name()));
        colAdminEventoRecinto.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getRecinto().getNombre()));

        colAdminCompraId.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getIdCompra()));
        colAdminCompraUsuario.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getUsuario().getNombreCompleto()));
        colAdminCompraEvento.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEvento().getNombre()));
        colAdminCompraEstado.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombreEstado()));
        colAdminCompraTotal.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getTotal()));

        colIncidenciaId.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getIdIncidencia()));
        colIncidenciaTipo.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTipo()));
        colIncidenciaEntidad.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEntidadAfectada()));
        colIncidenciaFecha.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFecha().toLocalDate().toString()));

        tablaAdminUsuarios.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, usuario) -> cargarUsuarioSeleccionado(usuario));
        tablaRecintos.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, recinto) -> cargarRecintoSeleccionado(recinto));
        tablaZonas.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, zona) -> cargarZonaSeleccionada(zona));
        tablaAsientos.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, asiento) -> cargarAsientoSeleccionado(asiento));
        tablaAdminEventos.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, evento) -> cargarEventoSeleccionado(evento));
    }

    private void cargarUsuarioSeleccionado(Usuario usuario) {
        if (usuario == null) {
            return;
        }
        txtAdminUsuarioNombre.setText(usuario.getNombreCompleto());
        txtAdminUsuarioCorreo.setText(usuario.getCorreoElectronico());
        txtAdminUsuarioTelefono.setText(usuario.getNumeroTelefono());
    }

    private void cargarRecintoSeleccionado(Recinto recinto) {
        if (recinto == null) {
            return;
        }
        txtRecintoNombre.setText(recinto.getNombre());
        txtRecintoDireccion.setText(recinto.getDireccion());
        txtRecintoCiudad.setText(recinto.getCiudad());
        comboRecintoZona.getSelectionModel().select(recinto);
        comboEventoRecinto.getSelectionModel().select(recinto);
    }

    private void cargarZonaSeleccionada(Zona zona) {
        if (zona == null) {
            return;
        }
        txtZonaNombre.setText(zona.getNombre());
        txtZonaCapacidad.setText(String.valueOf(zona.getCapacidad()));
        txtZonaPrecio.setText(String.format("%.0f", zona.getPrecioBase()));
        comboTipoZona.getSelectionModel().select(zona.getTipoZona());
        comboZonaAsiento.getSelectionModel().select(zona);
    }

    private void cargarAsientoSeleccionado(Asiento asiento) {
        if (asiento == null) {
            return;
        }
        txtAsientoFila.setText(asiento.getFila());
        txtAsientoNumero.setText(String.valueOf(asiento.getNumero()));
        comboEstadoAsiento.getSelectionModel().select(asiento.getEstadoAsiento());
    }

    private void cargarEventoSeleccionado(Evento evento) {
        if (evento == null) {
            comboEventoEstado.getSelectionModel().select(EstadoEvento.PUBLICADO);
            comboEventoEstado.setDisable(true);
            return;
        }
        txtEventoNombre.setText(evento.getNombre());
        txtEventoCategoria.setText(evento.getCategoria());
        txtEventoDescripcion.setText(evento.getDescripcion());
        txtEventoCiudad.setText(evento.getCiudad());
        dpEventoFecha.setValue(evento.getFechaHora().toLocalDate());
        comboEventoRecinto.getSelectionModel().select(evento.getRecinto());
        comboEventoEstado.getSelectionModel().select(evento.getEstadoEvento());
        comboEventoEstado.setDisable(false);
    }

    @FXML
    /**
     * Exporta el reporte general en formato CSV.
     */
    private void exportarReporteCSV() {
        String nombreArchivo = "reporte_metricas_generales.csv";
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(reporteMetricasGenerales());
            mensaje("Reporte CSV generado: " + nombreArchivo);
        } catch (IOException ex) {
            mensaje("Error generando CSV: " + ex.getMessage());
        }
    }

    @FXML
    /**
     * Exporta el reporte general en formato PDF.
     */
    private void exportarReportePDF() {
        String nombreArchivo = "reporte_metricas_generales.pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();
            document.add(new Paragraph("REPORTE OPERATIVO: METRICAS GENERALES"));
            document.add(new Paragraph("Rango: " + textoRangoReporte()));
            document.add(new Paragraph(" "));
            for (String linea : reporteMetricasGenerales().split("\\R")) {
                document.add(new Paragraph(linea));
            }
            document.close();
            mensaje("Reporte PDF generado: " + nombreArchivo);
        } catch (Exception ex) {
            mensaje("Error generando PDF: " + ex.getMessage());
        }
    }

    private void refrescarZonas() {
        Recinto recinto = comboRecintoZona.getValue();
        if (recinto == null) {
            tablaZonas.setItems(FXCollections.observableArrayList());
            comboZonaAsiento.setItems(FXCollections.observableArrayList());
            return;
        }
        tablaZonas.setItems(FXCollections.observableArrayList(recinto.getZonas()));
        comboZonaAsiento.setItems(FXCollections.observableArrayList(recinto.getZonas()));
        if (!comboZonaAsiento.getItems().isEmpty()) {
            comboZonaAsiento.getSelectionModel().selectFirst();
        }
    }

    private void refrescarAsientos() {
        Zona zona = comboZonaAsiento.getValue();
        if (zona == null) {
            tablaAsientos.setItems(FXCollections.observableArrayList());
            return;
        }
        tablaAsientos.setItems(FXCollections.observableArrayList(zona.getAsientos()));
    }

    /**
     * Actualiza las metricas y graficos de reportes.
     */
    private void refrescarMetricas() {
        lblMetricasResumen.setText(
                "Eventos: " + metricas.totalEventos()
        );
        cargarDetalleVentas();
        cargarOcupacionZona();
        cargarGraficoServiciosAdicionales();
        cargarGraficoTopEventos();
    }

    private void cargarDetalleVentas() {
        lblTotalVentas.setText("Total ventas: $" + String.format("%.0f", totalVentas()));
        lblVentasRealizadas.setText("Ventas realizadas: " + ventasRealizadas());
        lblRangoVentas.setText("Rango: " + textoRangoReporte());
    }

    private void cargarOcupacionZona() {
        StringBuilder sb = new StringBuilder();
        ocupacionPorZona().forEach((zona, valores) ->
                sb.append(zona).append(": ")
                        .append(valores[0]).append("/")
                        .append(valores[1]).append('\n')
        );
        txtOcupacionZona.setText(sb.toString());
    }

    private void cargarGraficoServiciosAdicionales() {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Servicios");
        ingresosPorServicio().forEach((servicio, total) ->
                serie.getData().add(new XYChart.Data<>(servicio, total))
        );
        chartServiciosAdicionales.getData().setAll(serie);
    }

    private void cargarGraficoTopEventos() {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Entradas");
        topEventosPorEntradas().forEach((evento, entradas) ->
                serie.getData().add(new XYChart.Data<>(evento, entradas))
        );
        chartTopEventos.getData().setAll(serie);
    }

    /**
     * Construye el texto completo del reporte general.
     *
     * @return texto del reporte
     */
    private String reporteMetricasGenerales() {
        return "VENTAS\n"
                + reporteVentas()
                + "\nOCUPACION POR ZONA\n"
                + reporteOcupacion()
                + "\nINGRESOS POR SERVICIO\n"
                + reporteServicios()
                + "\nTOP EVENTOS\n"
                + reporteTopEventos();
    }

    private String reporteVentas() {
        StringBuilder sb = new StringBuilder();
        sb.append("totalVentas,").append(totalVentas()).append('\n');
        sb.append("ventasRealizadas,").append(ventasRealizadas()).append('\n');
        sb.append("rango,").append(textoRangoReporte()).append('\n');
        return sb.toString();
    }

    private String reporteOcupacion() {
        StringBuilder sb = new StringBuilder("zona,ocupados,capacidad\n");
        ocupacionPorZona().forEach((zona, valores) ->
                sb.append(zona).append(',')
                        .append(valores[0]).append(',')
                        .append(valores[1]).append('\n')
        );
        return sb.toString();
    }

    private String reporteServicios() {
        StringBuilder sb = new StringBuilder("servicio,ingresos\n");
        double total = 0;
        for (Map.Entry<String, Double> entry : ingresosPorServicio().entrySet()) {
            total += entry.getValue();
            sb.append(entry.getKey()).append(',')
                    .append(entry.getValue()).append('\n');
        }
        sb.append("TOTAL,").append(total).append('\n');
        return sb.toString();
    }

    private String reporteTopEventos() {
        StringBuilder sb = new StringBuilder("evento,entradas\n");
        topEventosPorEntradas().forEach((evento, entradas) ->
                sb.append(evento).append(',')
                        .append(entradas).append('\n')
        );
        return sb.toString();
    }

    /**
     * Calcula ingresos agrupados por tipo de servicio.
     *
     * @return mapa con ingresos por servicio
     */
    private Map<String, Double> ingresosPorServicio() {
        Map<String, Double> ingresos = new LinkedHashMap<>();
        ingresos.put("VIP", 0.0);
        ingresos.put("Seguro", 0.0);
        ingresos.put("Parqueadero", 0.0);
        ingresos.put("Merchandising", 0.0);

        for (Compra compra : sistema.getCompras()) {
            if (!compraEnRango(compra) || !compraVendida(compra)) {
                continue;
            }
            // Solo se suman servicios de compras vendidas y dentro del rango.
            for (ServicioAdicional servicio : compra.getServiciosAdicionales()) {
                String tipo = tipoServicio(servicio);
                if (tipo != null) {
                    ingresos.merge(tipo, servicio.getPrecio(), Double::sum);
                }
            }
        }
        return ingresos;
    }

    /**
     * Calcula ocupacion por zona.
     *
     * @return mapa con ocupados y capacidad por zona
     */
    private Map<String, int[]> ocupacionPorZona() {
        Map<String, int[]> ocupacion = new LinkedHashMap<>();
        for (Recinto recinto : sistema.getRecintos()) {
            for (Zona zona : recinto.getZonas()) {
                int[] valores = ocupacion.computeIfAbsent(zona.getNombre(), key -> new int[]{0, 0});
                valores[0] += zona.consultarOcupacion();
                valores[1] += zona.getCapacidad();
            }
        }
        if (ocupacion.isEmpty()) {
            ocupacion.put("Sin zonas", new int[]{0, 0});
        }
        return ocupacion;
    }

    /**
     * Calcula los eventos con mas entradas vendidas.
     *
     * @return mapa con el top de eventos
     */
    private Map<String, Integer> topEventosPorEntradas() {
        Map<String, Integer> entradasPorEvento = new LinkedHashMap<>();
        for (Evento evento : sistema.getEventos()) {
            entradasPorEvento.put(evento.getNombre(), 0);
        }
        for (Compra compra : sistema.getCompras()) {
            if (!compraEnRango(compra) || !compraVendida(compra)) {
                continue;
            }
            entradasPorEvento.merge(
                    compra.getEvento().getNombre(),
                    compra.getEntradas().size(),
                    Integer::sum
            );
        }

        List<Map.Entry<String, Integer>> ordenadas = new ArrayList<>(entradasPorEvento.entrySet());
        ordenadas.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        Map<String, Integer> top = new LinkedHashMap<>();
        int limite = Math.min(5, ordenadas.size());
        for (int i = 0; i < limite; i++) {
            Map.Entry<String, Integer> entry = ordenadas.get(i);
            top.put((i + 1) + ". " + entry.getKey(), entry.getValue());
        }
        if (top.isEmpty()) {
            top.put("Sin ventas", 0);
        }
        return top;
    }

    private double totalVentas() {
        double total = 0;
        for (Compra compra : sistema.getCompras()) {
            if (compraEnRango(compra) && compraVendida(compra)) {
                total += compra.getTotal();
            }
        }
        return total;
    }

    private int ventasRealizadas() {
        int total = 0;
        for (Compra compra : sistema.getCompras()) {
            if (compraEnRango(compra) && compraVendida(compra)) {
                total++;
            }
        }
        return total;
    }

    private double totalServicios() {
        double total = 0;
        for (Double valor : ingresosPorServicio().values()) {
            total += valor;
        }
        return total;
    }

    private int totalEntradasVendidas() {
        int total = 0;
        for (Compra compra : sistema.getCompras()) {
            if (compraEnRango(compra) && compraVendida(compra)) {
                total += compra.getEntradas().size();
            }
        }
        return total;
    }

    private String tipoServicio(ServicioAdicional servicio) {
        String descripcion = servicio.getDescripcion().toLowerCase();
        if (descripcion.contains("vip")) {
            return "VIP";
        }
        if (descripcion.contains("seguro")) {
            return "Seguro";
        }
        if (descripcion.contains("parqueadero")) {
            return "Parqueadero";
        }
        if (descripcion.contains("merch")) {
            return "Merchandising";
        }
        return null;
    }

    private boolean compraVendida(Compra compra) {
        String estado = compra.getNombreEstado();
        return "PAGADA".equals(estado) || "CONFIRMADA".equals(estado);
    }

    private boolean compraEnRango(Compra compra) {
        LocalDate fecha = compra.getFechaCreacion().toLocalDate();
        LocalDate inicio = dpReporteInicio.getValue();
        LocalDate fin = dpReporteFin.getValue();
        return (inicio == null || !fecha.isBefore(inicio))
                && (fin == null || !fecha.isAfter(fin));
    }

    private String textoRangoReporte() {
        String inicio = dpReporteInicio.getValue() == null ? "sin inicio" : dpReporteInicio.getValue().toString();
        String fin = dpReporteFin.getValue() == null ? "sin fin" : dpReporteFin.getValue().toString();
        return inicio + " a " + fin;
    }

    private int totalZonas() {
        int total = 0;
        for (Recinto recinto : sistema.getRecintos()) {
            total += recinto.getZonas().size();
        }
        return total;
    }

    /**
     * Busca la zona a la que pertenece un asiento.
     *
     * @param asiento asiento buscado
     * @return zona encontrada o null
     */
    private Zona buscarZonaDeAsiento(Asiento asiento) {
        if (asiento == null) {
            return null;
        }
        for (Recinto recinto : sistema.getRecintos()) {
            for (Zona zona : recinto.getZonas()) {
                for (Asiento actual : zona.getAsientos()) {
                    if (actual == asiento || actual.getIdAsiento().equals(asiento.getIdAsiento())) {
                        return zona;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Busca el recinto al que pertenece una zona.
     *
     * @param zonaBuscada zona buscada
     * @return recinto encontrado o null
     */
    private Recinto buscarRecintoDeZona(Zona zonaBuscada) {
        if (zonaBuscada == null) {
            return null;
        }
        for (Recinto recinto : sistema.getRecintos()) {
            for (Zona zona : recinto.getZonas()) {
                if (zona == zonaBuscada || zona.getIdZona().equals(zonaBuscada.getIdZona())) {
                    return recinto;
                }
            }
        }
        return null;
    }

    private void mensaje(String mensaje) {
        lblAdminMensaje.setText(mensaje);
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
