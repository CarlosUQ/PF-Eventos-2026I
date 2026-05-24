package model;

/**
 * Representa una entrada para un evento.
 *
 * La entrada puede estar asociada a una zona y a un asiento.
 */
public class Entrada {

    private String idEntrada;

    private Zona zona;

    private Asiento asiento;

    private double precioFinal;

    private EstadoEntrada estadoEntrada;

    /**
     * Crea una entrada con su zona, asiento, precio y estado.
     *
     * @param idEntrada identificador de la entrada
     * @param zona zona a la que pertenece la entrada
     * @param asiento asiento asociado a la entrada
     * @param precioFinal precio final de la entrada
     * @param estadoEntrada estado inicial de la entrada
     */
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

    /**
     * Activa la entrada.
     */
    public void activarEntrada() {
        estadoEntrada = EstadoEntrada.ACTIVA;
    }

    /**
     * Usa la entrada si se encuentra activa.
     */
    public void usarEntrada() {

        if (puedeUsarse()) {
            estadoEntrada = EstadoEntrada.USADA;
        }
    }

    /**
     * Anula la entrada.
     */
    public void anularEntrada() {
        estadoEntrada = EstadoEntrada.ANULADA;
    }

    /**
     * Indica si la entrada esta activa.
     *
     * @return true si la entrada esta activa
     */
    public boolean estaActiva() {
        return estadoEntrada == EstadoEntrada.ACTIVA;
    }

    /**
     * Indica si la entrada puede usarse.
     *
     * @return true si la entrada esta activa
     */
    public boolean puedeUsarse() {
        return estadoEntrada == EstadoEntrada.ACTIVA;
    }

    /**
     * Indica si el asiento de la entrada esta disponible.
     *
     * @return true si no hay asiento asignado o si el asiento esta disponible
     */
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
