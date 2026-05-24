package model;

import java.io.IOException;

public interface IExportadorReporte {
    void exportar(Compra compra) throws IOException;
}
