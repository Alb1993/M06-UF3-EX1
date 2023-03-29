/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package albert.m06uf3ex1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Albert
 */
public class Post {

    private LocalDateTime data;
    private String autor;
    private String titol;
    private String text;
    private List<Integer> valoracio;

    public Post() {
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Integer> getValoracio() {
        return valoracio;
    }

    public void setValoracio(int valoracio) {
        this.valoracio.add(valoracio);
    }

    public void setValoracions(List<Integer> valoracio) {
        this.valoracio = valoracio;
    }

    public String getTitol() {
        return titol;
    }

    public String getText() {
        return text;
    }


    public LocalDateTime getData() {
        return data;
    }

    public String getAutor() {
        return autor;
    }

    public Post(LocalDateTime data, String autor, String titol, String text) {
        this.data = data;
        this.autor = autor;
        this.titol = titol;
        this.text = text;
        this.valoracio = new ArrayList<>();
    }

    //Funcion simple para calcular la media de nuestro Post.
    public int getValoracioMitja(){
        int media=0;
        for(int i=0; i<this.valoracio.size();i++){
        media=media + valoracio.get(i);
        }
        media = media/this.valoracio.size();
        return media;
    }

}
