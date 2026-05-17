package model;

public class UsuarioComun extends Usuario {

    public UsuarioComun(String idUsuario,
                        String nombreCompleto,
                        String correoElectronico,
                        String numeroTelefono,
                        String contrasena) {

        super(idUsuario,
                nombreCompleto,
                correoElectronico,
                numeroTelefono,
                contrasena);
    }
}