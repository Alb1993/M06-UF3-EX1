/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package albert.m06uf3ex1;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Albert
 */
// Clase de mapeo de documentos MongoDB
public class mapeo {

    /**
     * *
     * Mapeo de un Documento Post.
     *
     * @return Document.
     */
    public static Document mapToDocument(Post e) {
        /**
         * *
         * Mapeo de la clase Post y anidado del documento valoraciones.
         */
        Document doc = new Document();
        Document ret = new Document("autor", e.getAutor())
                .append("titol", e.getTitol())
                .append("text", e.getText())
                .append("data", e.getData())
                .append("valoracions", doc);

        return ret;
    }

    public static Post mapFromDocument(Document d) {
        Post post = new Post();
        post.setAutor(d.getString("autor"));
        Date fecha = d.get("data",java.util.Date.class);
        LocalDateTime localDateTime = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        post.setData(localDateTime);
        post.setText(d.getString("text"));
        post.setTitol(d.getString("titol"));
        Document valoracions =(Document) d.get("valoracions");
        if(!valoracions.isEmpty()){
            List<Integer> valoraciones = (List<Integer>) valoracions/*d.get("valoracions")*/;
            post.setValoracions(valoraciones);  
        }
        return post;
    }

}
