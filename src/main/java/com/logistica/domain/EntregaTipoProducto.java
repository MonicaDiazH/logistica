package com.logistica.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "entrega_tipo_producto")
public class EntregaTipoProducto implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(name = "precio" , scale = 2 )
    @NotNull(message = "No puede estar vacio el campo precio")
    private BigDecimal precio; 

    @Column(name = "cantidad" )
    @NotNull(message = "No puede estar vacio el campo cantidad")
    private Integer cantidad; 

    @Column(name = "total" , scale = 2 )
    @NotNull(message = "No puede estar vacio el campo total")
    private BigDecimal total; 

    @JoinColumn(name = "entrega_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Entrega entrega; 

    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private TipoProducto tipoProducto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EntregaTipoProducto that = (EntregaTipoProducto) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
