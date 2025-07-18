package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase utilitaria que proporciona métodos estáticos para formatear
 * valores monetarios y fechas según la configuración regional (locale) especificada.
 * <p>
 * Permite convertir cantidades numéricas a cadenas con formato monetario y
 * formatear objetos Date en cadenas legibles según las convenciones regionales.
 * </p>
 *
 * @author
 */
public class FormateadorUtils {

    /**
     * Formatea una cantidad numérica como una cadena representando moneda,
     * según la configuración regional proporcionada.
     *
     * @param cantidad cantidad numérica a formatear
     * @param locale   configuración regional que determina el formato de moneda
     * @return cadena con la cantidad formateada como moneda según el locale
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return formatoMoneda.format(cantidad);
    }

    /**
     * Formatea una fecha como una cadena legible según la configuración regional proporcionada,
     * utilizando el formato de fecha MEDIUM.
     *
     * @param fecha  objeto Date que se desea formatear
     * @param locale configuración regional que determina el formato de fecha
     * @return cadena con la fecha formateada según el locale
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }

}
