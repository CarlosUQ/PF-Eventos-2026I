package model;

public class Entrada {

    private String idEntrada;

    private Zona zona;

    private Asiento asiento;

    private double precioFinal;

    private EstadoEntrada estadoEntrada;

    public Entrada(String idEntrada,
                   Zona zona,
                   Asiento asiento,
                   double precioFinal,
                   EstadoEntrada estadoEntrada) {

        this.idEntrada = idEntrada;
        this.zona = zona;
        this.asiento = asiento;
        this.precioFinal = precioFinal;
        this.estadoEntrada = estadoEntrada;
    }

    public void activarEntrada() {
        estadoEntrada = EstadoEntrada.ACTIVA;
    }

    public void usarEntrada() {

        if (puedeUsarse()) {
            estadoEntrada = EstadoEntrada.USADA;
        }
    }

    public void anularEntrada() {
        estadoEntrada = EstadoEntrada.ANULADA;
    }

    public boolean estaActiva() {
        return estadoEntrada == EstadoEntrada.ACTIVA;
    }

    public boolean puedeUsarse() {
        return estadoEntrada == EstadoEntrada.ACTIVA;
    }

    public boolean tieneAsientoDisponible() {
        if (asiento == null) {
            return true;
        }

        return asiento.estaDisponible();
    }


    public String getIdEntrada() {
        return idEntrada;
    }

    public Zona getZona() {
        return zona;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public EstadoEntrada getEstadoEntrada() {
        return estadoEntrada;
    }

    public void setEstadoEntrada(EstadoEntrada estadoEntrada) {
        this.estadoEntrada = estadoEntrada;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "idEntrada='" + idEntrada + '\'' +
                ", precioFinal=" + precioFinal +
                ", estadoEntrada=" + estadoEntrada +
                '}';
    }
}
