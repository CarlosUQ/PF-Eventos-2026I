package model;

public interface ISujeto {
    void agregarObservador(IObservador obs);
    void eliminarObservador(IObservador obs);
    void notificarObservadores(String mensaje);
}