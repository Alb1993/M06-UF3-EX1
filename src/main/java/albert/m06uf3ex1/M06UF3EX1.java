/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package albert.m06uf3ex1;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import com.mongodb.client.model.Updates;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.bson.Document;

/**
 *
 * @author Albert
 */
public class M06UF3EX1 {

    public static Scanner in = new Scanner(System.in);
    public static MongoCollection<Document> coleccion;
    public static boolean exit = false;

    public static void main(String[] args) {
        coleccion = MongoSession.getInstance().getColleccio();
        while (exit == false) {
            muestraMenu();
            int opcion = in.nextInt();
            in.nextLine();
            gestionaMenu(opcion);
        }

    }

    public static void gestionaMenu(int opcion) {
        switch (opcion) {
            //Crear un Post.
            case 1:
                Post post = creaPost();
                Document doc = mapeo.mapToDocument(post);
                coleccion.insertOne(doc);
                break;
            //Visualizar Posts entre dos Fechas y horas.
            case 2:
                LocalDateTime ini = generaFecha("inicio");
                LocalDateTime fin = generaFecha("final");
                List<Document> docs = listarDocumentos(ini, fin);
                mostrarDocumentos(docs);
                break;
            //Borrar Posts entre dos Fechas y horas.
            case 3:
                LocalDateTime iniDel = generaFecha("inicio");
                LocalDateTime finDel = generaFecha("final");
                List<Document> docsDel = listarDocumentos(iniDel, finDel);
                borrarDocumentos(docsDel);
                break;
            //Listar documentos uno a uno y valorar el seleccionado.
            case 4:
                LocalDateTime iniList = generaFecha("inicio");
                LocalDateTime finList = generaFecha("final");
                in.nextLine();
                List<Document> docsList = listarDocumentos(iniList, finList);
                valoraPost(docsList);
                break;
            case 5:
                exit = true;
                break;
        }
    }

    //Funcion que visualizara los documentos ya filtrados por la consola.
    public static void borrarDocumentos(List<Document> docs) {
        for (Document documento : docs) {
            coleccion.deleteOne(documento);
        }
    }

    //Funcion que visualizara los documentos ya filtrados por la consola.
    public static void mostrarDocumentos(List<Document> docs) {
        for (Document documento : docs) {
            System.out.println(documento.toJson());
        }
    }

    //Funcion que generará un listado de documentos segun su fecha de inicio y su fecha de fin.
    public static List<Document> listarDocumentos(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Document> documents = new ArrayList<>();
        //LocalDateTime->data.
        coleccion.find(and(gte("data", startDateTime.toInstant(ZoneOffset.UTC)), lte("data", endDateTime.toInstant(ZoneOffset.UTC)))).into(documents);
        return documents;
    }

    //Funcion donde crearemos los objetos Post.
    public static Post creaPost() {
        Post post = new Post();

        System.out.println("Introduce Autor:");
        String autor = in.nextLine();

        System.out.println("Introduce Titol:");
        String titol = in.nextLine();

        System.out.println("Introduce texto:");
        String texto = in.nextLine();

        LocalDateTime data = LocalDateTime.now();

        post.setAutor(autor);
        post.setTitol(titol);
        post.setText(texto);
        post.setData(data);

        return post;
    }

    public static void valoraPost(List<Document> docsList) {
        boolean valorado = false;
        for (Document documento : docsList) {
            while (valorado == false) {
                System.out.println(documento.toJson());
                System.out.println("Desea Valorar? (Y,N)");
                String opcion = in.nextLine();
                if (opcion.equals("Y")) {
                    Post post = mapeo.mapFromDocument(documento);
                    //json = Documento.toJson();
                    LocalDateTime fecha = post.getData();
                    System.out.println("Inserta Valoracion: (1-5)");
                    int valoracion = in.nextInt();
                    
                    int num = Integer.parseInt(System.currentTimeMillis()+"");
                    
                    Document filter = new Document("_id",documento.getObjectId("_id"));
                    Document actualizacion = new Document("$set", new Document("valoracions.valoracio" + num, valoracion));

                    //Actualizar el post original en la colección
                    coleccion.updateOne(filter, actualizacion);
                    valorado=true;
                    
                }else{
                valorado=true;
                }
            }
        }
    }

    //Funcion con el mostreo de prints del menu por consola.
    public static void muestraMenu() {
        System.out.println("==== BIENVENIDO =====");
        System.out.println("Que deseas hacer?");
        System.out.println("1. Crear Post");
        System.out.println("2. Ver Posts entre dos fechas i horas.");
        System.out.println("3. Eliminar Posts entre dos fechas i horas.");
        System.out.println("4. Valorar un post");
        System.out.println("5. Salir");
    }

    //Funcion encargada de generar LocalDateTimes
    public static LocalDateTime generaFecha(String concat) {
        //Insertamos una fecha i una hora escritas por consola.
        System.out.println("Introduce fecha " + concat + ": (Formato dd-MM-yyyy)");
        String fecha = in.nextLine();
        System.out.println("Introduce hora " + concat + ": (Formato HH:mm:ss)");
        String hora = in.nextLine();

        //Definimos los patrones de formateo de fecha y hora.
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        //Parseamos los strings escritos en objetos LocalDate y LocalTime.
        LocalDate localDate = LocalDate.parse(fecha, dateFormatter);
        LocalTime localTime = LocalTime.parse(hora, timeFormatter);

        //Concatenamos los Objetos LocalDate y LocalTime en un objeto LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        //Devolvemos el objeto
        return localDateTime;
    }
}
