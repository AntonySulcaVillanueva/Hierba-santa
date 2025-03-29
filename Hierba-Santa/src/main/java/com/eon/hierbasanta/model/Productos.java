package com.eon.hierbasanta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproducto")
    private Integer idproducto;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria" ,referencedColumnName = "idcategoria")
    private Categorias categoria;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "gramos")
    private String gramos;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "descuento")
    private Integer descuento;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "imagen1")
    private String imagen1;

    @Column(name = "imagen2")
    private String imagen2;

    @Column(name = "imagen3")
    private String imagen3;
}
