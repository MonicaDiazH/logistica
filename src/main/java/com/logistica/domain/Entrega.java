package com.logistica.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "entrega")
public class Entrega implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha_registro" )
    @NotNull(message = "No puede estar vacio el campo fechaRegistro")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaRegistro;

    @Column(name = "fecha_entrega" )
    @NotNull(message = "No puede estar vacio el campo fechaEntrega")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaEntrega;

    @Column(name = "precio_normal" , scale = 2 )
    @NotNull(message = "No puede estar vacio el campo precioNormal")
    private BigDecimal precioNormal;

    @Column(name = "descuento" , scale = 2 )
    @NotNull(message = "No puede estar vacio el campo descuento")
    private BigDecimal descuento;

    @Column(name = "precio_final" , scale = 2 )
    @NotNull(message = "No puede estar vacio el campo precioFinal")
    private BigDecimal precioFinal;

    @Column(name = "numero_guia" )
    @NotBlank(message = "No puede estar vacio el campo numeroGuia")
    @Size(max = 10, message = "El campo numeroGuia excede la longitud permitida")
    private String numeroGuia;

    @Column(name = "tipo_entrega" )
    @NotBlank(message = "No puede estar vacio el campo tipoEntrega")
    @Size(max = 10, message = "El campo tipoEntrega excede la longitud permitida")
    private String tipoEntrega;

    @OneToMany(mappedBy = "entrega", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<EntregaTipoProducto> entregaTipoProductoes;

    @JoinColumn(name = "puerto_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Puerto puerto;

    @JoinColumn(name = "bodega_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Bodega bodega;

    @JoinColumn(name = "transporte_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Transporte transporte;

    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente cliente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Entrega entrega = (Entrega) o;
        return id != null && Objects.equals(id, entrega.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}