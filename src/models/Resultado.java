package models;

public class Resultado {
    private String algoritmo;
    private String escenario;
    private int sample;
    private double tiempoNano;
    private double tiempoMillis;
    
    public Resultado(String algoritmo, String escenario, int sample, double tiempoNano, double tiempoMillis) {
        this.algoritmo = algoritmo;
        this.escenario = escenario;
        this.sample = sample;
        this.tiempoNano = tiempoNano;
        this.tiempoMillis = tiempoMillis;
    }

    @Override
    public String toString() {
        return "Resultado [algoritmo=" + algoritmo + ", escenario=" + escenario + ", sample=" + sample + ", tiempoNano="
                + tiempoNano + ", tiempoMillis=" + tiempoMillis + "]";
    }

    
}
