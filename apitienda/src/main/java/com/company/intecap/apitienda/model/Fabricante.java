package com.company.intecap.apitienda.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fabricante")
public class Fabricante  implements Serializable {

    private static final long serialVersionUID = 2L;
    // esta constante es para que se pueda serializar el objeto y se pueda enviar por la red

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", length = 30)
    private String nombre;

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

    public Fabricante(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Fabricante(Long id) {
        this.id = id;
    }

    public Fabricante(String nombre) {
        this.nombre = nombre;
    }

    public Fabricante() {
    }
}
