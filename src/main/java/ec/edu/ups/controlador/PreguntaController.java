package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.RespuestaSegu;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.CedulaValidar;
import ec.edu.ups.util.ContrasenaValidar;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.Registrarse.RecuperarView;
import ec.edu.ups.vista.Registrarse.RegistrarView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
/**
 * Controlador encargado de manejar la lógica relacionada con preguntas de seguridad
 * para el registro de usuarios y recuperación de contraseñas.
 *
 * Gestiona eventos en las vistas LoginView, RegistrarView y RecuperarView.
 * También se encarga de la internacionalización de mensajes.
 *
 */
public class PreguntaController {
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final RegistrarView registraseView;
    private final PreguntaDAO preguntaDAO;
    private final RecuperarView recuperarView;
    private int cont;
    private MensajeInternacionalizacionHandler Internacionalizar;
    /**
     * Constructor principal que inicializa el controlador y configura los eventos de las vistas.
     *
     * @param usuarioDAO DAO para manejar usuarios.
     * @param loginView Vista de login.
     * @param registraseView Vista de registro de usuario.
     * @param preguntaDAO DAO para manejar preguntas de seguridad.
     * @param recuperarView Vista para recuperación de contraseña.
     * @param Internacionalizar Manejador de internacionalización de mensajes.
     */
    public PreguntaController(UsuarioDAO usuarioDAO, LoginView loginView, RegistrarView registraseView,
                              PreguntaDAO preguntaDAO, RecuperarView recuperarView, MensajeInternacionalizacionHandler Internacionalizar) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.registraseView = registraseView;
        this.preguntaDAO = preguntaDAO;
        this.recuperarView = recuperarView;
        this.Internacionalizar = Internacionalizar;
        this.cont = 1;
        configurarEventosEnVista();
    }
    /**
     * Configura los eventos iniciales en la vista de login.
     */
    private void configurarEventosEnVista(){

        loginView.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                registrarse();
            }
        });
        loginView.getBtnOlvidoContraseña().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(loginView.getTxtUsername().getText().isEmpty()){
                    recuperarView.mostrarMensaje(Internacionalizar.get("recuperacion.usuario.vacio"));
                    return;
                }
                if(usuarioDAO.buscarPorUsuario(loginView.getTxtUsername().getText()) == null){
                    recuperarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.noencontrado"));
                    return;
                }
                loginView.setVisible(false);
                configurarEventosEnOlvidada();
                recuperarView.setVisible(true);

            }
        });
    }
    /**
     * Navega a la vista de registro y configura sus eventos.
     */
    private void registrarse() {
        loginView.setVisible(false);
        configurarEventosEnRegistrarse();
        registraseView.setVisible(true);
    }
    /**
     * Configura los eventos en la vista de registro.
     */
    private void configurarEventosEnRegistrarse() {
        registraseView.getTxtUsuario().setText("");
        registraseView.getTxtContrasenia().setText("");
        final int[] contadorPreguntasRespondidas = {0};
        List<RespuestaSegu> preguntasRespondidas = new ArrayList<>();
        cargarPregunta(cont);

        quitarActionListeners(registraseView.getBtnGuardar());
        quitarActionListeners(registraseView.getBtnRegistar());

        registraseView.getBtnRegistar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (preguntasRespondidas.size() < 3) {
                    registraseView.mostrarMensaje(Internacionalizar.get("registro.preguntas.requeridas"));
                    return;
                }
                // Validar campos vacíos
                if (registraseView.getTxtUsuario().getText().isEmpty() ||
                        registraseView.getTxtContrasenia().getText().isEmpty() ||
                        registraseView.getTxtNombre().getText().isEmpty() ||
                        registraseView.getTxtTelefono().getText().isEmpty() ||
                        registraseView.getTxtCorreo().getText().isEmpty() ||
                        registraseView.getTxtAnio().getText().isEmpty() ||
                        registraseView.getCbxDia().getSelectedItem() == null ||
                        registraseView.getCbxMes().getSelectedItem() == null) {
                    registraseView.mostrarMensaje(Internacionalizar.get("mensaje.completar.campos"));
                    return;
                }
                // Validar usuario existente
                if (usuarioDAO.buscarPorUsuario(registraseView.getTxtUsuario().getText()) != null) {
                    registraseView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.existe"));
                    return;
                }

                String usuario = registraseView.getTxtUsuario().getText();
                String contrasenia = registraseView.getTxtContrasenia().getText();
                String nombre = registraseView.getTxtNombre().getText();
                String telefono = registraseView.getTxtTelefono().getText();
                String correo = registraseView.getTxtCorreo().getText();

                int dia = (Integer) registraseView.getCbxDia().getSelectedItem();
                String mesSeleccionado = (String) registraseView.getCbxMes().getSelectedItem();
                int mes = obtenerIndiceMes(mesSeleccionado);
                if (mes == -1) {
                    registraseView.mostrarMensaje(Internacionalizar.get("mes.invalido"));
                    return;
                }
                int anio = Integer.parseInt(registraseView.getTxtAnio().getText());
                GregorianCalendar fecha = new GregorianCalendar(anio, mes, dia);

                try {
                    Usuario usuario1 = new Usuario(usuario, Rol.USUARIO, contrasenia, nombre, correo, telefono, fecha);
                    usuario1.setRespuestaSegu(preguntasRespondidas);
                    usuarioDAO.crear(usuario1);

                    registraseView.vaciarCampo();
                    registraseView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.creado"));
                    loginView.setVisible(true);
                    registraseView.dispose();

                    cont = 1;
                    contadorPreguntasRespondidas[0] = 0;
                } catch (CedulaValidar ex) {
                    registraseView.mostrarMensaje(Internacionalizar.get("mensaje.cedula.invalida") + ": " + ex.getMessage());
                } catch (ContrasenaValidar ex) {
                    registraseView.mostrarMensaje(Internacionalizar.get("mensaje.contraseña.invalida") + ": " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    registraseView.mostrarMensaje(Internacionalizar.get("mensaje.anio.invalido"));
                } catch (Exception ex) {
                    registraseView.mostrarMensaje(Internacionalizar.get("mensaje.error.general") + ": " + ex.getMessage());
                }

            }
        });



        registraseView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(cont > 13){
                    return;
                }
                if(!registraseView.getTxtPregunta().getText().isEmpty()){
                    contadorPreguntasRespondidas[0]++;
                    RespuestaSegu respuestaSegu = new RespuestaSegu(preguntaDAO.buscarPorCodigo(cont), registraseView.getTxtPregunta().getText());
                    preguntasRespondidas.add(respuestaSegu);
                }
                cont++;
                cargarPregunta(cont);
                registraseView.getTxtPregunta().setText("");

            }
        });
    }
    /**
     * Carga una pregunta de seguridad por su código.
     *
     * @param codigo Código de la pregunta a cargar.
     */
    private void cargarPregunta(int codigo) {
        Pregunta pregunta = preguntaDAO.buscarPorCodigo(codigo);
        if (pregunta == null) {
            registraseView.getLblEnunciado().setText(Internacionalizar.get("mensaje.pregunta.noencontrada"));
            return;
        }
        registraseView.getLblPreguntas().setText(Internacionalizar.get("registro.numero.pregunta") + " " + codigo);
        registraseView.getLblEnunciado().setText(Internacionalizar.get(pregunta.getTexto()));
    }
    /**
     * Configura los eventos para la recuperación de contraseña.
     */
    private void configurarEventosEnOlvidada() {
        Usuario usuarioRecuperacion = usuarioDAO.buscarPorUsuario(loginView.getTxtUsername().getText());
        List<RespuestaSegu> preguntas = usuarioRecuperacion.getRespuestaSegu();

        if (preguntas == null || preguntas.isEmpty()) {
            recuperarView.mostrarMensaje(Internacionalizar.get("recuperacion.sin.preguntas"));
            loginView.setVisible(true);
            recuperarView.dispose();
            return;
        }

        randomizarListaPreguntaRespondida(preguntas);
        List<Integer> codigos = new ArrayList<>();
        List<String> respuestas = new ArrayList<>();
        for (RespuestaSegu preguntaRespondida : preguntas) {
            codigos.add(preguntaRespondida.getPregunta().getId());
            respuestas.add(preguntaRespondida.getRespuesta());
        }

        final int[] iteradorCodigo = {0};
        final int[] correctas = {0};

        cargarPreguntaOlvidada(codigos.get(iteradorCodigo[0]));

        quitarActionListeners(recuperarView.getBtnRestablecer());
        quitarActionListeners(recuperarView.getBtnContinuar());

        recuperarView.getBtnContinuar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                if(correctas[0] >= 2){
                    recuperarView.getBtnContinuar().setEnabled(false);
                    recuperarView.getBtnRestablecer().setEnabled(true);
                    recuperarView.getLblEnunciado().setText("");
                    recuperarView.getLblPreguntas().setText("");
                    recuperarView.getTxtContraseña().setText("");
                    recuperarView.getLblEnunciado().setText(Internacionalizar.get("recuperacion.nueva.contrasena"));
                    return;
                }

                if (recuperarView.getTxtContraseña().getText().isEmpty() ||
                        !recuperarView.getTxtContraseña().getText().toLowerCase().equals(respuestas.get(iteradorCodigo[0]).toLowerCase())) {
                    recuperarView.mostrarMensaje(Internacionalizar.get("recuperacion.respuesta.erronea"));
                    return;
                }
                iteradorCodigo[0]++;
                correctas[0]++;
                recuperarView.getTxtContraseña().setText("");
                cargarPreguntaOlvidada(codigos.get(iteradorCodigo[0]));

            }
        });

        recuperarView.getBtnRestablecer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (recuperarView.getTxtContraseña().getText().isEmpty()) {
                    recuperarView.mostrarMensaje(Internacionalizar.get("recuperacion.nueva.contrasena"));
                    return;
                }

                String nuevaContrasenia = recuperarView.getTxtContraseña().getText();
                usuarioRecuperacion.setContrasenia(nuevaContrasenia);
                usuarioDAO.actualizar(usuarioRecuperacion);
                recuperarView.mostrarMensaje(Internacionalizar.get("recuperacion.contrasena.actualizada"));
                recuperarView.getTxtContraseña().setText("");
                recuperarView.getBtnContinuar().setEnabled(true);
                recuperarView.getBtnRestablecer().setEnabled(false);
                iteradorCodigo[0] = 0;
                correctas[0] = 0;
                loginView.setVisible(true);
                recuperarView.dispose();
            }
        });
    }

    private void cargarPreguntaOlvidada(int codigo){
        Pregunta pregunta = preguntaDAO.buscarPorCodigo(codigo);
        if (pregunta == null) {
            recuperarView.getLblEnunciado().setText(Internacionalizar.get("mensaje.pregunta.noencontrada"));
            return;
        }
        recuperarView.getLblPreguntas().setText(Internacionalizar.get("registro.numero.pregunta") + " " + codigo);
        recuperarView.getLblEnunciado().setText(Internacionalizar.get(pregunta.getTexto()));
    }
    /**
     * Configura los eventos para cambiar Idioma
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        Internacionalizar.setLenguaje(lenguaje, pais);
        registraseView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        recuperarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        cargarPregunta(cont);
    }
    /**
     * Configura metodo de randomizar Pregunta
     */
    private void randomizarListaPreguntaRespondida(List<RespuestaSegu> lista) {
        List<RespuestaSegu> listaRandomizada = new LinkedList<>();
        while (!lista.isEmpty()) {
            int randomIndex = (int) (Math.random() * lista.size());
            listaRandomizada.add(lista.remove(randomIndex));
        }
        lista.addAll(listaRandomizada);
    }

    private void quitarActionListeners(JButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }

    private int obtenerIndiceMes(String mesSeleccionado) {
        Map<String, Integer> meses = new HashMap<>();
        meses.put(Internacionalizar.get("mes.enero"), 0);
        meses.put(Internacionalizar.get("mes.febrero"), 1);
        meses.put(Internacionalizar.get("mes.marzo"), 2);
        meses.put(Internacionalizar.get("mes.abril"), 3);
        meses.put(Internacionalizar.get("mes.mayo"), 4);
        meses.put(Internacionalizar.get("mes.junio"), 5);
        meses.put(Internacionalizar.get("mes.julio"), 6);
        meses.put(Internacionalizar.get("mes.agosto"), 7);
        meses.put(Internacionalizar.get("mes.septiembre"), 8);
        meses.put(Internacionalizar.get("mes.octubre"), 9);
        meses.put(Internacionalizar.get("mes.noviembre"), 10);
        meses.put(Internacionalizar.get("mes.diciembre"), 11);

        return meses.getOrDefault(mesSeleccionado, -1);
    }


}
