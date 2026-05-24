package model;

/**
 * Define un objeto que puede notificar a observadores.
 */
public interface ISujeto {

    /**
     * Agrega un observador.
     *
     * @param obs observador que se agrega
     */
    void agregarObservador(IObservador obs);

    /**
     * Elimina un observador.
     *
     * @param obs observador que se elimina
     */
    void eliminarObservador(IObservador obs);

    /**
     * Notifica un mensaje a los observadores.
     *
     * @param mensaje mensaje enviado
     */
    void notificarObservadores(String mensaje);
}
