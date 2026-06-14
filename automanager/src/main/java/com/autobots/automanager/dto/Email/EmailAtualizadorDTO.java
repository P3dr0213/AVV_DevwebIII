package com.autobots.automanager.dto.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailAtualizadorDTO {
    @NotNull(message = "O id do e-mail  obrigatrio")
    private Long id;

    @Email(message = "O formato do e-mail  invlido")
    private String endereco;
}