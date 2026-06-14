package com.autobots.automanager.dto.CredencialCodigoBarra;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CredencialQRExibirDTO extends RepresentationModel<CredencialQRExibirDTO> {
    private Long id;
    private Long codigo;
    private Boolean inativo;
}