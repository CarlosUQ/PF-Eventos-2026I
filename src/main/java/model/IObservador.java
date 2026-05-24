package model;

/**
 * Define un objeto que puede recibir notificaciones.
 */
public interface IObservador {

    /**
     * Recibe un mensaje de notificacion.
     *
     * @param mensaje mensaje recibido
     */
    void actualizar(String mensaje);
}
