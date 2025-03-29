package com.eon.hierbasanta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPedido {

    @Column(name = "id_producto_pedido")
    private Integer idProductoPedido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "descuento")
    private Integer descuento;

    @Column(name = "gramos")
    private Integer gramos;

    @Column(name = "otro_descuento", nullable = true)
    private Double otroDescuento = 0.0;
}