package com.logistica.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "puerto")
public class Puerto implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(name = "nombre" )
    @NotBlank(message = "No puede estar vacio el campo nombre")
    @Size(max = 50, message = "El campo nombre excede la longitud permitida")
    private String nombre; 

    @Column(name = "direccion" )
    @NotBlank(message = "No puede estar vacio el campo direccion")
    @Size(max = 200, message = "El campo direccion excede la longitud permitida")
    private String direccion; 

    @Column(name = "tipo" )
    @NotBlank(message = "No puede estar vacio el campo tipo")
    @Size(max = 10, message = "El campo tipo excede la longitud permitida")
    private String tipo;

    @OneToMany(mappedBy = "puerto", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Entrega> entregaes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Puerto puerto = (Puerto) o;
        return id != null && Objects.equals(id, puerto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}