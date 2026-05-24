package model;

import java.io.FileWriter;
import java.io.IOException;

public class ExportadorCSVAdapter implements IExportadorReporte {

    @Override
    public void exportar(Compra compra) throws IOException {

        String fileName = "compra_" + compra.getIdCompra() + ".csv";

        FileWriter writer = new FileWriter(fileName);

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