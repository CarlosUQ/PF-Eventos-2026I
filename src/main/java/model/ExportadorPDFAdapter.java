package model;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Exporta el detalle de una compra en formato PDF.
 */
public class ExportadorPDFAdapter implements IExportadorReporte {

    /**
     * Crea un archivo PDF con los datos principales de la compra.
     *
     * @param compra compra que se desea exportar
     * @throws IOException si ocurre un error al crear el archivo
     */
    @Override
    public void exportar(Compra compra) throws IOException {

        try {

            String fileName = "compra_" + compra.getIdCompra() + ".pdf";

            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            // El documento se abre, se escriben los datos y luego se cierra.
            document.open();

            document.add(new Paragraph("DETALLE DE COMPRA"));
            document.add(new Paragraph("ID: " + compra.getIdCompra()));
            document.add(new Paragraph("Usuario: " + compra.getUsuario().getNombreCompleto()));
            document.add(new Paragraph("Evento: " + compra.getEvento().getNombre()));
            document.add(new Paragraph("Total: " + compra.getTotal()));
            document.add(new Paragraph("Estado: " + compra.getNombreEstado()));

            document.close();

            System.out.println("PDF generado correctamente: " + fileName);

        } catch (Exception e) {
            throw new IOException("Error generando PDF", e);
        }
    }
}
