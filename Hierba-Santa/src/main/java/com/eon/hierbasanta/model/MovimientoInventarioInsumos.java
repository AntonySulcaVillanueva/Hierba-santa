package com.eon.hierbasanta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "movimiento_inventario_insumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventarioInsumos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_insumo", referencedColumnName = "id_insumo")
    private Insumos insumo;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "fecha_movimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    private Empleado empleado;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    @Column(name = "motivo")
    private String motivo;
}