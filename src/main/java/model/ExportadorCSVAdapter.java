package model;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Exporta el detalle de una compra en formato CSV.
 */
public class ExportadorCSVAdapter implements IExportadorReporte {

    /**
     * Crea un archivo CSV con los datos principales de la compra.
     *
     * @param compra compra que se desea exportar
     * @throws IOException si ocurre un error al escribir el archivo
     */
    @Override
    public void exportar(Compra compra) throws IOException {

        String fileName = "compra_" + compra.getIdCompra() + ".csv";

        FileWriter writer = new FileWriter(fileName);

        // Se escribe una fila de encabezado y una fila con la compra.
        writer.write("ID,Usuario,Evento,Total,Estado\n");

        writer.write(
                compra.getIdCompra() + "," +
                        compra.getUsuario().getNombreCompleto() + "," +
                        compra.getEvento().getNombre() + "," +
                        compra.getTotal() + "," +
                        compra.getNombreEstado()
        );

        writer.close();

        System.out.println("CSV generado correctamente: " + fileName);
    }
}
