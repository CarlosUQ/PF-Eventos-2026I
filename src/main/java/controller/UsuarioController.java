package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Asiento;
import model.Compra;
import model.Entrada;
import model.EstadoEntrada;
import model.EstadoEvento;
import model.Evento;
import model.ExportadorCSVAdapter;
import model.ExportadorPDFAdapter;
import model.IExportadorReporte;
import model.Recinto;
import model.SeguroCancelacionDecorator;
import model.ServicioAdicional;
import model.ServicioBase;
import model.SistemaCompraFacade;
import model.SistemaEventos;
import model.Usuario;
import model.UsuarioComun;
import model.VIPDecorator;
import model.Zona;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controlador de la pantalla del usuario.
 *
 * Permite registrar usuarios, filtrar eventos, crear compras,
 * consultar historial y exportar compras.
 */
public class UsuarioController {

    private final AppContext context = AppContext.getInstance();
    private final SistemaEventos sistema = context.getSistema();
    private final SistemaCompraFacade comprasFacade = context.getComprasFacade();

    @FXML private ComboBox<Usuario> comboUsuario;
    @FXML private TextField txtNombreRegistro;
    @FXML private TextField txtCorreoRegistro;
    @FXML private TextField txtTelefonoRegistro;
    @FXML private TextField txtNombrePerfil;
    @FXML private TextField txtCorreoPerfil;
    @FXML private TextField txtTelefonoPerfil;
    @FXML private TextField txtFiltroCiudad;
    @FXML private TextField txtFiltroCategoria;
    @FXML private TextField txtFiltroPrecio;
    @FXML private DatePicker filtroFecha;
    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableColumn<Evento, String> colEventoNombre;
    @FXML private TableColumn<Evento, String> colEventoCategoria;
    @FXML private TableColumn<Evento, String> colEventoCiudad;
    @FXML private TableColumn<Evento, String> colEventoFecha;
    @FXML private TableColumn<Evento, Number> colEventoDesde;
    @FXML private TextArea txtDetalleEvento;
    @FXML private ComboBox<Zona> comboZona;
    @FXML private ComboBox<Asiento> comboAsiento;
    @FXML private ComboBox<String> comboPago;
    @FXML private CheckBox chkVip;
    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkMerch;
    @FXML private CheckBox chkParqueadero;
    @FXML private Label lblTotalCompra;
    @FXML private Label lblMensajeUsuario;
    @FXML private TextField txtFiltroHistorialEvento;
    @FXML private ComboBox<String> comboFiltroHistorialEstado;
    @FXML private DatePicker dpFiltroHistorialFecha;
    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, String> colCompraId;
    @FXML private TableColumn<Compra, String> colCompraEvento;
    @FXML private TableColumn<Compra, String> colCompraEstado;
    @FXML private TableColumn<Compra, Number> colCompraTotal;
    @FXML private TableColumn<Compra, String> colCompraFecha;

    @FXML
    /**
     * Inicializa combos, tablas y datos del usuario.
     */
    private void initialize() {
        configurarCombos();
        configurarTablas();
        context.addDataChangeListener(this::refrescarPanelUsuario);
        recargarUsuarios();
        recargarEventos();
        actualizarCompras();
        cargarPerfil();
    }

    @FXML
    /**
     * Recarga los datos visibles del panel de usuario.
     */
    private void refrescarPanelUsuario() {
        Usuario usuarioSeleccionado = comboUsuario.getValue();
        recargarUsuarios();
        if (usuarioSeleccionado != null && comboUsuario.getItems().contains(usuarioSeleccionado)) {
            comboUsuario.getSelectionModel().select(usuarioSeleccionado);
        }
        recargarEventos();
        actualizarDetalleEvento();
        actualizarCompras();
        if (tablaEventos != null) {
            tablaEventos.refresh();
        }
        if (tablaCompras != null) {
            tablaCompras.refresh();
        }
    }

    @FXML
    private void registrarUsuario() {
        if (estaVacio(txtNombreRegistro.getText()) || estaVacio(txtCorreoRegistro.getText())) {
            mostrarMensaje("Nombre y correo son obligatorios.");
            return;
        }

        Usuario usuario = new UsuarioComun(
                context.nextId("U", sistema.getUsuarios().size()),
                txtNombreRegistro.getText().trim(),
                txtCorreoRegistro.getText().trim(),
                txtTelefonoRegistro.getText().trim(),
                "1234"
        );
        usuario.agregarMetodoPago("NEQUI");
        usuario.agregarMetodoPago("PSE");
        sistema.registrarUsuario(usuario);
        context.setUsuarioActual(usuario);
        recargarUsuarios();
        comboUsuario.getSelectionModel().select(usuario);
        limpiarRegistro();
        context.notifyDataChanged();
        mostrarMensaje("Usuario registrado e iniciado: " + usuario.getNombreCompleto());
    }

    @FXML
    private void aplicarPerfil() {
        Usuario usuario = comboUsuario.getValue();
        if (usuario == null) {
            mostrarMensaje("Selecciona un usuario.");
            return;
        }
        usuario.actualizarPerfil(
                txtNombrePerfil.getText().trim(),
                txtCorreoPerfil.getText().trim(),
                txtTelefonoPerfil.getText().trim()
        );
        recargarUsuarios();
        comboUsuario.getSelectionModel().select(usuario);
        context.notifyDataChanged();
        mostrarMensaje("Perfil actualizado.");
    }

    @FXML
    private void aplicarFiltros() {
        recargarEventos();
    }

    @FXML
    private void limpiarFiltros() {
        txtFiltroCiudad.clear();
        txtFiltroCategoria.clear();
        txtFiltroPrecio.clear();
        filtroFecha.setValue(null);
        recargarEventos();
    }

    @FXML
    private void aplicarFiltrosHistorial() {
        actualizarCompras();
    }

    @FXML
    private void limpiarFiltrosHistorial() {
        txtFiltroHistorialEvento.clear();
        comboFiltroHistorialEstado.getSelectionModel().select("TODOS");
        dpFiltroHistorialFecha.setValue(null);
        actualizarCompras();
    }

    @FXML
    /**
     * Crea una compra con la seleccion actual de evento, zona y servicios.
     */
    private void crearCompra() {
        Usuario usuario = comboUsuario.getValue();
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();
        Zona zona = comboZona.getValue();
        Asiento asiento = comboAsiento.getValue();

        if (usuario == null || evento == null || zona == null) {
            mostrarMensaje("Selecciona usuario, evento y zona.");
            return;
        }

        try {
            String idCompra = context.nextId("C", sistema.getCompras().size());
            // Se crea una entrada con el precio base de la zona seleccionada.
            Entrada entrada = new Entrada(
                    "T-" + idCompra,
                    zona,
                    asiento,
                    zona.getPrecioBase(),
                    EstadoEntrada.ACTIVA
            );
            Compra compra = comprasFacade.realizarCompra(
                    idCompra,
                    usuario,
                    evento,
                    comboPago.getValue(),
                    Collections.singletonList(entrada),
                    crearServicios(idCompra),
                    null
            );
            actualizarCompras();
            actualizarDetalleEvento();
            actualizarAsientos();
            tablaCompras.getSelectionModel().select(compra);
            context.notifyDataChanged();
            mostrarMensaje("Compra creada y pagada: " + compra.getIdCompra());
        } catch (Exception ex) {
            mostrarMensaje("No se pudo crear la compra: " + ex.getMessage());
        }
    }

    @FXML
    private void confirmarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) {
            mostrarMensaje("Selecciona una compra.");
            return;
        }
        compra.confirmarCompra();
        actualizarCompras();
        context.notifyDataChanged();
        mostrarMensaje("Compra confirmada.");
    }

    @FXML
    private void cancelarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) {
            mostrarMensaje("Selecciona una compra.");
            return;
        }
        try {
            compra.cancelarCompra();
            actualizarCompras();
            actualizarAsientos();
            context.notifyDataChanged();
            mostrarMensaje("Compra cancelada.");
        } catch (IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    @FXML
    private void exportarCSV() {
        exportarCompra(new ExportadorCSVAdapter(), "CSV");
    }

    @FXML
    private void exportarPDF() {
        exportarCompra(new ExportadorPDFAdapter(), "PDF");
    }

    /**
     * Configura combos y eventos de cambio de la pantalla.
     */
    private void configurarCombos() {
        comboUsuario.setConverter(new StringConverter<Usuario>() {
            @Override
            public String toString(Usuario usuario) {
                return usuario == null ? "" : usuario.getIdUsuario() + " - " + usuario.getNombreCompleto();
            }

            @Override
            public Usuario fromString(String value) {
                return null;
            }
        });
        comboUsuario.valueProperty().addListener((obs, oldValue, newValue) -> {
            context.setUsuarioActual(newValue);
            cargarPerfil();
            actualizarCompras();
        });

        comboZona.setConverter(new StringConverter<Zona>() {
            @Override
            public String toString(Zona zona) {
                if (zona == null) {
                    return "";
                }
                return zona.getNombre() + " - $" + String.format("%.0f", zona.getPrecioBase())
                        + " (" + zona.obtenerCantidadDisponible() + " disp.)";
            }

            @Override
            public Zona fromString(String value) {
                return null;
            }
        });
        comboZona.valueProperty().addListener((obs, oldValue, newValue) -> actualizarAsientos());

        comboAsiento.setConverter(new StringConverter<Asiento>() {
            @Override
            public String toString(Asiento asiento) {
                return asiento == null ? "Sin asiento" : asiento.getFila() + "-" + asiento.getNumero();
            }

            @Override
            public Asiento fromString(String value) {
                return null;
            }
        });
        comboAsiento.valueProperty().addListener((obs, oldValue, newValue) -> calcularTotal());

        comboPago.setItems(FXCollections.observableArrayList(
                "NEQUI", "DAVIPLATA", "PAYPAL", "PSE", "TARJETA_CREDITO", "TARJETA_DEBITO"
        ));
        comboPago.getSelectionModel().select("NEQUI");

        comboFiltroHistorialEstado.setItems(FXCollections.observableArrayList(
                "TODOS", "CREADA", "PAGADA", "CONFIRMADA", "CANCELADA", "REEMBOLSADA", "INCIDENCIA"
        ));
        comboFiltroHistorialEstado.getSelectionModel().select("TODOS");

        chkVip.selectedProperty().addListener((obs, oldValue, newValue) -> calcularTotal());
        chkSeguro.selectedProperty().addListener((obs, oldValue, newValue) -> calcularTotal());
        chkMerch.selectedProperty().addListener((obs, oldValue, newValue) -> calcularTotal());
        chkParqueadero.selectedProperty().addListener((obs, oldValue, newValue) -> calcularTotal());
    }

    /**
     * Configura columnas de eventos y compras.
     */
    private void configurarTablas() {
        colEventoNombre.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombre()));
        colEventoCategoria.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCategoria()));
        colEventoCiudad.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCiudad()));
        colEventoFecha.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFechaHora().toLocalDate().toString()));
        colEventoDesde.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(precioMinimo(data.getValue())));
        tablaEventos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> actualizarDetalleEvento());

        colCompraId.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getIdCompra()));
        colCompraEvento.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEvento().getNombre()));
        colCompraEstado.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombreEstado()));
        colCompraTotal.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getTotal()));
        colCompraFecha.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFechaCreacion().toLocalDate().toString()));
    }

    private void recargarUsuarios() {
        comboUsuario.setItems(FXCollections.observableArrayList(sistema.getUsuarios()));
        if (context.getUsuarioActual() != null) {
            comboUsuario.getSelectionModel().select(context.getUsuarioActual());
        } else if (!comboUsuario.getItems().isEmpty()) {
            comboUsuario.getSelectionModel().selectFirst();
        }
    }

    /**
     * Recarga eventos aplicando los filtros actuales.
     */
    private void recargarEventos() {
        List<Evento> eventos = new ArrayList<>();
        for (Evento evento : sistema.getEventos()) {
            if (cumpleFiltros(evento)) {
                eventos.add(evento);
            }
        }
        tablaEventos.setItems(FXCollections.observableArrayList(eventos));
        if (!eventos.isEmpty()) {
            tablaEventos.getSelectionModel().selectFirst();
        }
        tablaEventos.refresh();
    }

    /**
     * Valida si un evento cumple los filtros de busqueda.
     *
     * @param evento evento que se revisa
     * @return true si debe mostrarse
     */
    private boolean cumpleFiltros(Evento evento) {
        if (evento.getEstadoEvento() != EstadoEvento.PUBLICADO) {
            return false;
        }
        String ciudad = txtFiltroCiudad.getText() == null ? "" : txtFiltroCiudad.getText().trim().toLowerCase();
        String categoria = txtFiltroCategoria.getText() == null ? "" : txtFiltroCategoria.getText().trim().toLowerCase();
        LocalDate fecha = filtroFecha.getValue();

        if (!ciudad.isEmpty() && !evento.getCiudad().toLowerCase().contains(ciudad)) {
            return false;
        }
        if (!categoria.isEmpty() && !evento.getCategoria().toLowerCase().contains(categoria)) {
            return false;
        }
        if (fecha != null && !evento.getFechaHora().toLocalDate().equals(fecha)) {
            return false;
        }
        if (!estaVacio(txtFiltroPrecio.getText())) {
            try {
                double maximo = Double.parseDouble(txtFiltroPrecio.getText().trim());
                if (precioMinimo(evento) > maximo) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return true;
    }

    /**
     * Muestra la informacion del evento seleccionado.
     */
    private void actualizarDetalleEvento() {
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();
        if (evento == null) {
            txtDetalleEvento.clear();
            comboZona.getItems().clear();
            return;
        }

        Recinto recinto = evento.getRecinto();
        txtDetalleEvento.setText(
                evento.getNombre() + "\n"
                        + evento.getDescripcion() + "\n"
                        + "Categoria: " + evento.getCategoria() + "\n"
                        + "Ciudad: " + evento.getCiudad() + "\n"
                        + "Fecha: " + evento.getFechaHora() + "\n"
                        + "Recinto: " + recinto.getNombre() + " - " + recinto.getDireccion() + "\n"
                        + "Aforo: " + recinto.obtenerCapacidadTotal()
        );
        comboZona.setItems(FXCollections.observableArrayList(evento.obtenerZonas()));
        if (!comboZona.getItems().isEmpty()) {
            comboZona.getSelectionModel().selectFirst();
        }
        calcularTotal();
    }

    /**
     * Actualiza los asientos disponibles de la zona seleccionada.
     */
    private void actualizarAsientos() {
        Zona zona = comboZona.getValue();
        if (zona == null) {
            comboAsiento.getItems().clear();
            calcularTotal();
            return;
        }
        comboAsiento.setItems(FXCollections.observableArrayList(zona.consultarAsientosDisponibles()));
        if (!comboAsiento.getItems().isEmpty()) {
            comboAsiento.getSelectionModel().selectFirst();
        }
        comboZona.setButtonCell(comboZona.getButtonCell());
        calcularTotal();
    }

    /**
     * Actualiza la tabla de compras del usuario actual.
     */
    private void actualizarCompras() {
        Usuario usuario = comboUsuario == null ? null : comboUsuario.getValue();
        if (usuario == null) {
            tablaCompras.setItems(FXCollections.observableArrayList());
            return;
        }
        List<Compra> comprasFiltradas = new ArrayList<>();
        for (Compra compra : usuario.getCompras()) {
            if (cumpleFiltrosHistorial(compra)) {
                comprasFiltradas.add(compra);
            }
        }
        tablaCompras.setItems(FXCollections.observableArrayList(comprasFiltradas));
        tablaCompras.refresh();
    }

    /**
     * Valida si una compra cumple los filtros del historial.
     *
     * @param compra compra que se revisa
     * @return true si debe mostrarse
     */
    private boolean cumpleFiltrosHistorial(Compra compra) {
        String evento = txtFiltroHistorialEvento == null || txtFiltroHistorialEvento.getText() == null
                ? ""
                : txtFiltroHistorialEvento.getText().trim().toLowerCase();
        String estado = comboFiltroHistorialEstado == null ? "TODOS" : comboFiltroHistorialEstado.getValue();
        LocalDate fecha = dpFiltroHistorialFecha == null ? null : dpFiltroHistorialFecha.getValue();

        if (!evento.isEmpty() && !compra.getEvento().getNombre().toLowerCase().contains(evento)) {
            return false;
        }
        if (estado != null && !"TODOS".equals(estado) && !estado.equals(compra.getNombreEstado())) {
            return false;
        }
        if (fecha != null && !compra.getFechaCreacion().toLocalDate().equals(fecha)) {
            return false;
        }
        return true;
    }

    private void cargarPerfil() {
        Usuario usuario = comboUsuario == null ? null : comboUsuario.getValue();
        if (usuario == null) {
            return;
        }
        txtNombrePerfil.setText(usuario.getNombreCompleto());
        txtCorreoPerfil.setText(usuario.getCorreoElectronico());
        txtTelefonoPerfil.setText(usuario.getNumeroTelefono());
    }

    /**
     * Busca el precio base mas bajo de las zonas del evento.
     *
     * @param evento evento consultado
     * @return precio minimo
     */
    private double precioMinimo(Evento evento) {
        double menor = Double.MAX_VALUE;
        for (Zona zona : evento.obtenerZonas()) {
            menor = Math.min(menor, zona.getPrecioBase());
        }
        return menor == Double.MAX_VALUE ? 0 : menor;
    }

    /**
     * Calcula el total visible de la compra segun zona y servicios.
     */
    private void calcularTotal() {
        Zona zona = comboZona.getValue();
        double total = zona == null ? 0 : zona.getPrecioBase();
        if (chkVip.isSelected()) {
            total += 50000;
        }
        if (chkSeguro.isSelected()) {
            total += 20000;
        }
        if (chkMerch.isSelected()) {
            total += 30000;
        }
        if (chkParqueadero.isSelected()) {
            total += 15000;
        }
        lblTotalCompra.setText("Total: $" + String.format("%.0f", total));
    }

    /**
     * Crea la lista de servicios adicionales seleccionados.
     *
     * @param idCompra identificador de la compra
     * @return servicios seleccionados
     */
    private List<ServicioAdicional> crearServicios(String idCompra) {
        List<ServicioAdicional> servicios = new ArrayList<>();
        if (chkVip.isSelected()) {
            servicios.add(new VIPDecorator(new ServicioBase("S-VIP-" + idCompra, "Acceso", 0)));
        }
        if (chkSeguro.isSelected()) {
            servicios.add(new SeguroCancelacionDecorator(new ServicioBase("S-SEG-" + idCompra, "Seguro", 0)));
        }
        if (chkMerch.isSelected()) {
            servicios.add(new ServicioBase("S-MER-" + idCompra, "Merchandising", 30000));
        }
        if (chkParqueadero.isSelected()) {
            servicios.add(new ServicioBase("S-PAR-" + idCompra, "Parqueadero", 15000));
        }
        return servicios;
    }

    /**
     * Exporta la compra seleccionada.
     *
     * @param exportador exportador que se usa
     * @param tipo nombre del tipo de archivo
     */
    private void exportarCompra(IExportadorReporte exportador, String tipo) {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) {
            mostrarMensaje("Selecciona una compra.");
            return;
        }
        try {
            exportador.exportar(compra);
            mostrarMensaje("Reporte " + tipo + " generado para " + compra.getIdCompra());
        } catch (IOException ex) {
            mostrarMensaje("Error exportando " + tipo + ": " + ex.getMessage());
        }
    }

    private void limpiarRegistro() {
        txtNombreRegistro.clear();
        txtCorreoRegistro.clear();
        txtTelefonoRegistro.clear();
    }

    private void mostrarMensaje(String mensaje) {
        lblMensajeUsuario.setText(mensaje);
        if (mensaje.startsWith("No se pudo") || mensaje.startsWith("Error")) {
            new Alert(Alert.AlertType.WARNING, mensaje, ButtonType.OK).showAndWait();
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
