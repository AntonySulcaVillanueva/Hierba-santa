package com.eon.hierbasanta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insumos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumo")
    private Long idInsumo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", referencedColumnName = "idcategoria")
    private Categorias categoria;

    @Column(name = "unidad_de_medida")
    private String unidadDeMedida;

    @Column(name = "stock")
    private Double stock;

    @Column(name = "costo")
    private Double costo;

    @Column(name = "alerta_stock_minimo")
    private Double alertaStockMinimo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    private Proveedor proveedor;
}