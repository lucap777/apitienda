package com.company.intecap.apitienda.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "articulo")
public class Articulo implements Serializable{
    private static final long serialVersionUID = 1L;
    // esta constante es para que se pueda serializar el objeto y se pueda enviar por la red

    //definimos los atributos de la clase
    @Id //@Id es para que se cree una llave primaria en la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private float precio;

    //campo de la tabla fabricante
     //relacion de muchos a uno
    // (un articulo pertenece a una fabricante pero una fabricante puede tener muchos articulos)

    @ManyToOne(fetch = FetchType.LAZY) //FetchType.LAZY es para que no se cargue la categoria hasta que se llame (carga perezosa)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //ignora los atributos que no se necesitan(hadler: es para que no se cargue la categoria hasta que se llame)
    private Fabricante fabricante;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public Articulo(Long id, String nombre, float precio, Fabricante fabricante) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fabricante = fabricante;
    }

    public Articulo(String nombre, float precio, Fabricante fabricante) {
        this.nombre = nombre;
        this.precio = precio;
        this.fabricante = fabricante;
    }

    public Articulo(Long id) {
        this.id = id;
    }

    public Articulo() {
    }
}

