package model;

import java.io.IOException;

/**
 * Define un exportador de reportes de compra.
 */
public interface IExportadorReporte {

    /**
     * Exporta la informacion de una compra.
     *
     * @param compra compra que se desea exportar
     * @throws IOException si ocurre un error al crear el archivo
     */
    void exportar(Compra compra) throws IOException;
}
