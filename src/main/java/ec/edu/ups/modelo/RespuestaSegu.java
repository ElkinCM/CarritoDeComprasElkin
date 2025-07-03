package ec.edu.ups.modelo;

public class RespuestaSegu {
    private Pregunta pre;
    private String res;

    public RespuestaSegu(Pregunta pre, String res) {
        this.pre = pre;
        this.res = res;
    }

    public Pregunta getPre() {
        return pre;
    }

    public void setPre(Pregunta pre) {
        this.pre = pre;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public boolean ResCorrecta(String respuestaValida){
        if (this.res == null || respuestaValida == null){
            return false;
        }
        return this.res.trim().equalsIgnoreCase(respuestaValida.trim());
    }

    @Override
    public String toString() {
        return "RespuestaSegu{" +
                "pre='" + pre + '\'' +
                ", res='" + res + '\'' +
                '}';
    }
}
