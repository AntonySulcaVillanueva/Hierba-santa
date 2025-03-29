package com.eon.hierbasanta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    @Column(name = "metodo_de_pago")
    private String metodoDePago;

    @Column(name = "comentarios")
    private String comentarios;

    @Column(name = "fecha_pedido")
    @Temporal(TemporalType.DATE)
    private Date fechaPedido;

    @Column(name = "fecha_envio")
    private LocalDate fechaEnvio;

    @Column(name = "estado_pedido")
    private String estadoPedido;

    @Column(name = "direccion_envio")
    private String direccionEnvio;

    @ElementCollection
    @CollectionTable(name = "pedido_productos", joinColumns = @JoinColumn(name = "id_pedido"))
    private List<ProductoPedido> productos;

    @Column(name = "productos_ids")
    private String productosIds;

    @Column(name = "op_grabada")
    private BigDecimal opGrabada;

    @Column(name = "igv")
    private BigDecimal igv;

    @Column(name = "importe_total")
    private BigDecimal importeTotal;

    @Column(name = "redondeo_a_favor")
    private BigDecimal redondeoAFavor;

    @Column(name = "total_a_pagar")
    private BigDecimal totalAPagar;

    @PrePersist
    @PreUpdate
    private void calcularValores() {
        this.opGrabada = calcularOpGrabada();
        this.igv = calcularIGV();
        this.importeTotal = calcularImporteTotal();
        this.redondeoAFavor = calcularRedondeoAFavor();
        this.totalAPagar = calcularTotalAPagar();
        this.productosIds = productos.stream()
                .map(p -> p.getNombre()) // Assuming that the product name is unique
                .collect(Collectors.joining(","));
    }

    private BigDecimal calcularOpGrabada() {
        BigDecimal opGrabada = productos.stream()
                .map(p -> BigDecimal.valueOf(p.getCantidad())
                        .multiply(BigDecimal.valueOf(p.getPrecio()))
                        .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(p.getDescuento() + p.getOtroDescuento()).divide(BigDecimal.valueOf(100))))
                        .divide(BigDecimal.valueOf(1.18), 2, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return opGrabada.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularIGV() {
        return calcularOpGrabada().multiply(BigDecimal.valueOf(0.18)).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularImporteTotal() {
        BigDecimal importeTotal = productos.stream()
                .map(p -> BigDecimal.valueOf(p.getCantidad())
                        .multiply(BigDecimal.valueOf(p.getPrecio()))
                        .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(p.getDescuento() + p.getOtroDescuento()).divide(BigDecimal.valueOf(100)))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return importeTotal.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularRedondeoAFavor() {
        BigDecimal importeTotal = calcularImporteTotal();
        BigDecimal roundedTotal = importeTotal.setScale(1, RoundingMode.UP);
        return roundedTotal.subtract(importeTotal).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularTotalAPagar() {
        return calcularImporteTotal().add(calcularRedondeoAFavor()).setScale(2, RoundingMode.HALF_UP);
    }
}