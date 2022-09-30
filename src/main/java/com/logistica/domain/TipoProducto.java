package com.logistica.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "tipo_producto")
public class TipoProducto implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(name = "nombre" )
    @NotBlank(message = "No puede estar vacio el campo nombre")
    @Size(max = 100, message = "El campo nombre excede la longitud permitida")
    private String nombre; 

    @Column(name = "descripcion" )
    @NotBlank(message = "No puede estar vacio el campo descripcion")
    @Size(max = 200, message = "El campo descripcion excede la longitud permitida")
    private String descripcion; 

    @Column(name = "precio" , scale = 2 )
    @NotNull(message = "No puede estar vacio el campo precio")
    private BigDecimal precio; 

    @OneToMany(mappedBy = "tipoProducto", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<EntregaTipoProducto> entregaTipoProductoes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TipoProducto that = (TipoProducto) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}