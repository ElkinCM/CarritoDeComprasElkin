package ec.edu.ups.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que maneja la internacionalización de mensajes mediante la carga de
 * recursos desde archivos de propiedades basados en el lenguaje y país especificados.
 * <p>
 * Permite obtener mensajes localizados para la interfaz de usuario o cualquier
 * otro propósito que requiera soporte multi-idioma.
 * </p>
 *
 * Ejemplo de uso:
 * <pre>
 *     MensajeInternacionalizacionHandler mih = new MensajeInternacionalizacionHandler("es", "EC");
 *     String saludo = mih.get("saludo");
 * </pre>
 *
 * Los archivos de recursos deben estar en el classpath bajo el nombre "mensajes"
 * y contener las traducciones según los locales.
 *
 * @author
 */
public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    /**
     * Constructor que crea una instancia con el lenguaje y país especificados,
     * cargando el archivo de recursos correspondiente.
     *
     * @param lenguaje código ISO 639 de lenguaje (ejemplo: "es", "en")
     * @param pais     código ISO 3166 de país (ejemplo: "EC", "US")
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el mensaje localizado correspondiente a la clave especificada.
     *
     * @param key clave del mensaje en el archivo de propiedades
     * @return el mensaje localizado asociado a la clave
     * @throws java.util.MissingResourceException si la clave no existe
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Cambia el lenguaje y país activos y recarga el archivo de recursos
     * correspondiente.
     *
     * @param lenguaje código ISO 639 de lenguaje
     * @param pais     código ISO 3166 de país
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", this.locale);
    }

    /**
     * Devuelve el locale actual configurado.
     *
     * @return objeto {@link Locale} activo
     */
    public Locale getLocale() {
        return locale;
    }
}
