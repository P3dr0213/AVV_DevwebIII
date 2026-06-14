package com.autobots.automanager.dto.Email;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmailExibirDTO extends RepresentationModel<EmailExibirDTO> {
    private Long id;
    private String endereco;
}
