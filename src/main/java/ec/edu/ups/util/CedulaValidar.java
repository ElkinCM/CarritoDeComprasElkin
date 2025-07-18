package ec.edu.ups.util;

/**
 * Excepción personalizada que se lanza cuando una cédula ecuatoriana no es válida
 * según las reglas definidas para la validación.
 * <p>
 * Esta clase extiende {@link RuntimeException} y puede ser utilizada para
 * indicar errores específicos en la validación de cédulas.
 * </p>
 *
 * @author Elkin Chamba
 */
public class CedulaValidar extends RuntimeException {

    /**
     * Constructor que crea una excepción con un mensaje específico.
     *
     * @param message mensaje descriptivo del error de validación
     */
    public CedulaValidar(String message) {
        super(message);
    }
}
