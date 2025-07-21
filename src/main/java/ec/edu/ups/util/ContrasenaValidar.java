package ec.edu.ups.util;

/**
 * Excepción personalizada que se lanza cuando una contraseña no cumple con
 * los criterios de validación establecidos.
 * <p>
 * Esta clase extiende {@link RuntimeException} y se utiliza para indicar
 * errores específicos relacionados con la validación de contraseñas.
 * </p>
 *
 * @author
 */
public class ContrasenaValidar extends RuntimeException {

    /**
     * Constructor que crea una excepción con un mensaje descriptivo del error de validación.
     *
     * @param message mensaje que describe la razón de la excepción
     */
    public ContrasenaValidar(String message) {
        super(message);
    }
}
