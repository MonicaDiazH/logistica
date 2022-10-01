package com.logistica.domain.dto;

import com.logistica.domain.Entrega;
import com.logistica.domain.EntregaTipoProducto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EntregaDTO {
    private Entrega entrega;
    private Integer clienteId;
    private Integer bodegaId;
    private Integer puertoId;
    private Integer transporteId;
    private List<EntregaTipoProducto> entregaTipoProductos;
}