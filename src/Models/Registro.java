package Models;

public class Registro{

    private int id;
    private int noSemana;
    private String mes;
    private String razon;
    private Double monto;
    private String tipo;

    public Registro(int id, int noSemana, String mes, String razon, double monto, String tipo) {
        this.id = id;
        this.noSemana = noSemana;
        this.mes = mes;
        this.razon = razon;
        this.monto = monto;
        this.tipo = tipo;
    }

    public Registro(int noSemana, String mes, String razon, double monto, String tipo) {
        this.noSemana = noSemana;
        this.mes = mes;
        this.razon = razon;
        this.monto = monto;
        this.tipo = tipo;
    }

    public Registro(String razon, double monto) {
        this.razon = razon;
        this.monto = monto;
    }
    
    

    public Registro() {
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoSemana() {
        return noSemana;
    }

    public void setNoSemana(int noSemana) {
        this.noSemana = noSemana;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    

    @Override
    public String toString() {
        return getId() + " " + getNoSemana() + " " + getMes() + " " + getRazon() + " " + getMonto() + " " + getTipo();
    }
    
    

}