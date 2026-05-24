package model;

/**
 * Representa un usuario comun del sistema.
 */
public class UsuarioComun extends Usuario {

    /**
     * Crea un usuario comun con sus datos personales.
     *
     * @param idUsuario identificador del usuario
     * @param nombreCompleto nombre completo del usuario
     * @param correoElectronico correo electronico
     * @param numeroTelefono numero de telefono
     * @param contrasena contrasena del usuario
     */
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
