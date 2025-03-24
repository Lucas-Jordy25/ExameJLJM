package com.example.examejljm;


public class Revista {
    private String titulo;
    private String portada;

    public Revista(String titulo, String portada) {
        this.titulo = titulo;
        this.portada = portada;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPortada() {
        return portada;
    }
}
