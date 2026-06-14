package com.autobots.automanager.dto.Documento;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentoExibirDTO extends RepresentationModel<DocumentoExibirDTO> {
    private Long id;
    private String tipo;
    private String numero;
    private Long idCliente;
}
