package com.logistica.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
@Table(name = "transporte")
public class Transporte implements Serializable {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @JsonView
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(name = "descripcion" )
    @NotBlank(message = "No puede estar vacio el campo descripcion")
    @Size(max = 100, message = "El campo descripcion excede la longitud permitida")
    private String descripcion; 

    @Column(name = "identificacion" )
    @NotBlank(message = "No puede estar vacio el campo identificacion")
    @Size(max = 10, message = "El campo identificacion excede la longitud permitida")
    private String identificacion; 

    @OneToMany(mappedBy = "transporte", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<Entrega> entregaes;

    @JoinColumn(name = "tipo_transporte_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private TipoTransporte tipoTransporte;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transporte that = (Transporte) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}