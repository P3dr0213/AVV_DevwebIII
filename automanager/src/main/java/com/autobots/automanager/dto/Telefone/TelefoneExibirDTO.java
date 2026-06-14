package com.autobots.automanager.dto.Telefone;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TelefoneExibirDTO extends RepresentationModel<TelefoneExibirDTO> {
    private Long id;
    private String ddd;
    private String numero;
    private Long idCliente;
}
