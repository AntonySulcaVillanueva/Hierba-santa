package com.eon.hierbasanta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado", referencedColumnName = "id_empleado")
    private Empleado empleado;

    @NonNull
    @Column(name = "username")
    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String username;

    @Size(min = 5, max = 100, message = "La contraseña debe tener entre 5 y 100 caracteres")
    @Column(name = "password")
    private String password;

    @Email(message = "El correo electrónico debe tener un formato válido")
    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Column(name = "gmail")
    private String gmail;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void prePersist() {
        fechaRegistro = LocalDateTime.now();
        fechaModificacion = fechaRegistro;
    }

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}