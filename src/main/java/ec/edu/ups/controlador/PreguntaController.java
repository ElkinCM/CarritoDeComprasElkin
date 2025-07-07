package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.RespuestaSegu;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.Registrarse.RecuperarView;
import ec.edu.ups.vista.Registrarse.RegistrarView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class PreguntaController {
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final RegistrarView registraseView;
    private final PreguntaDAO preguntaDAO;
    private final RecuperarView recuperarView;
    private int cont;
    private MensajeInternacionalizacionHandler Internacionalizar;

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


    private void registrarse() {
        loginView.setVisible(false);
        configurarEventosEnRegistrarse();
        registraseView.setVisible(true);
    }


    private void configurarEventosEnRegistrarse() {
        registraseView.getTxtUsuario().setText("");
        registraseView.getTxtContrasenia().setText("");
        final int[] contadorPreguntasRespondidas = {0};
        List<RespuestaSegu> preguntasRespondidas = new ArrayList<>();
        cargarPregunta(cont);

        quitarActionListeners(registraseView.getBtnGuardar());
        quitarActionListeners(registraseView.getBtnRegistar());

        registraseView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(preguntasRespondidas.size() < 3){
                    registraseView.mostrarMensaje(Internacionalizar.get("registro.preguntas.requeridas"));
                    return;
                }
                if (registraseView.getTxtUsuario().getText().isEmpty() ||
                        registraseView.getTxtContrasenia().getText().isEmpty() ||
                        registraseView.getTxtNombre().getText().isEmpty() ||
                        registraseView.getTxtTelefono().getText().isEmpty() ||
                        registraseView.getTxtCorreo().getText().isEmpty() ||
                        registraseView.getTxtAnio().getText().isEmpty() ||
                        registraseView.getCbxDia().getSelectedItem() == null ||
                        registraseView.getCbxMes().getSelectedItem() == null ) {
                    registraseView.mostrarMensaje(Internacionalizar.get("mensaje.completar.campos"));
                    return;
                }
                if (usuarioDAO.buscarPorUsuario(registraseView.getTxtUsuario().getText()) != null) {
                    registraseView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.existe"));
                    return;
                }
                String usuario = registraseView.getTxtUsuario().getText();
                String contrasenia = registraseView.getTxtContrasenia().getText();
                String nombre = registraseView.getTxtNombre().getText();
                String telefono = registraseView.getTxtTelefono().getText();
                String correo = registraseView.getTxtCorreo().getText();
                GregorianCalendar fecha = new GregorianCalendar();
                int dia =(Integer) registraseView.getCbxDia().getSelectedItem();
                int mes = -1;
                String mesSeleccionado = (String) registraseView.getCbxMes().getSelectedItem();
                if (mesSeleccionado.equals(Internacionalizar.get("mes.enero"))) {
                    mes = 0;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.febrero"))) {
                    mes = 1;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.marzo"))) {
                    mes = 2;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.abril"))) {
                    mes = 3;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.mayo"))) {
                    mes = 4;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.junio"))) {
                    mes = 5;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.julio"))) {
                    mes = 6;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.agosto"))) {
                    mes = 7;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.septiembre"))) {
                    mes = 8;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.octubre"))) {
                    mes = 9;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.noviembre"))) {
                    mes = 10;
                } else if (mesSeleccionado.equals(Internacionalizar.get("mes.diciembre"))) {
                    mes = 11;
                } else {
                    registraseView.mostrarMensaje(Internacionalizar.get("mes.invalido"));
                    return;
                }
                int anio = Integer.parseInt(registraseView.getTxtAnio().getText()) ;
                fecha.set(anio, mes, dia);

                registraseView.vaciarCampo();

                Usuario usuario1 =  new Usuario(usuario, Rol.USUARIO, contrasenia,
                        nombre, correo, telefono, fecha);
                usuario1.setRespuestaSegu(preguntasRespondidas);
                usuarioDAO.crear(usuario1);
                registraseView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.creado"));
                loginView.setVisible(true);
                registraseView.dispose();
                cont = 1;
                contadorPreguntasRespondidas[0] = 0;
            }
        });

        registraseView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(cont > 12){
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

    private void cargarPregunta(int codigo){
        registraseView.getLblPreguntas().setText(Internacionalizar.get("registro.numero.pregunta") + " " + codigo);
        registraseView.getLblEnunciado().setText(Internacionalizar.get(preguntaDAO.buscarPorCodigo(codigo).getTexto()));
    }

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
        recuperarView.getLblPreguntas().setText(Internacionalizar.get("registro.numero.pregunta") + " " + codigo);
        recuperarView.getLblEnunciado().setText(Internacionalizar.get(preguntaDAO.buscarPorCodigo(codigo).getTexto()));
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        Internacionalizar.setLenguaje(lenguaje, pais);
        registraseView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        recuperarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        cargarPregunta(cont);
    }

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

}
