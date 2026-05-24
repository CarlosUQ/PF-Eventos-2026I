package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

/**
 * Controlador principal de la aplicacion.
 *
 * Muestra un resumen simple de los datos cargados.
 */
public class MainController {

    @FXML
    private Label resumenLabel;

    @FXML
    /**
     * Inicializa el resumen y lo actualiza cuando cambian los datos.
     */
    private void initialize() {
        AppContext.getInstance().addDataChangeListener(this::actualizarResumen);
        actualizarResumen();
    }

    /**
     * Actualiza el texto con cantidades generales del sistema.
     */
    private void actualizarResumen() {
        AppContext context = AppContext.getInstance();
        resumenLabel.setText(
                "Eventos: " + context.getSistema().getEventos().size()
                        + " | Usuarios: " + context.getSistema().getUsuarios().size()
                        + " | Compras: " + context.getSistema().getCompras().size()
                        + " | " + LocalDate.now()
        );
    }
}
