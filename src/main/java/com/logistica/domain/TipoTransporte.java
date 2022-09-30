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
@Table(name = "tipo_transporte")
public class TipoTransporte implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(name = "tipo" )
    @NotBlank(message = "No puede estar vacio el campo tipo")
    @Size(max = 15, message = "El campo tipo excede la longitud permitida")
    private String tipo; 

    @Column(name = "descripcion" )
    @NotBlank(message = "No puede estar vacio el campo descripcion")
    @Size(max = 50, message = "El campo descripcion excede la longitud permitida")
    private String descripcion; 

    @Column(name = "nombre_identificacion" )
    @NotBlank(message = "No puede estar vacio el campo nombreIdentificacion")
    @Size(max = 15, message = "El campo nombreIdentificacion excede la longitud permitida")
    private String nombreIdentificacion; 

    @OneToMany(mappedBy = "tipoTransporte", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Transporte> transportees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TipoTransporte that = (TipoTransporte) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}