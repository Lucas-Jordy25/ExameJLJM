package com.example.examejljm;
import java.util.List;

public class Article {
    int article_id;
    String section;
    String title;
    String doi;
    String abstractText; // Usar abstractText en lugar de "abstract" porque es palabra reservada en Java
    String date_published;
    List<Autor> authors; // Lista de autores
    List<galeria> galeys; // Lista de formatos de visualización (PDF/HTML)

    // Método para obtener la URL del PDF
    public String getPdfUrl() {
        for (galeria galley : galeys) {
            if ("PDF".equalsIgnoreCase(galley.label)) {
                return galley.UrlViewGalley;
            }
        }
        return null;
    }

    // Método para obtener la URL del HTML
    public String getHtmlUrl() {
        for (galeria galley : galeys) {
            if ("HTML".equalsIgnoreCase(galley.label)) {
                return galley.UrlViewGalley;
            }
        }
        return null;
    }

    // Método para obtener los nombres de los autores en un solo String
    public String getAuthorsString() {
        if (authors == null || authors.isEmpty()) {
            return "Autor desconocido";
        }
        StringBuilder authorsString = new StringBuilder();
        for (Autor author : authors) {
            authorsString.append(author.name).append(", ");
        }
        return authorsString.substring(0, authorsString.length() - 2);
    }
}