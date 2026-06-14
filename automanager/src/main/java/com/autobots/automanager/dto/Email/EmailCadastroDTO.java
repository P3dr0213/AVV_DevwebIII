package com.autobots.automanager.dto.Email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailCadastroDTO {
    @NotBlank(message = "O endereo de e-mail  obrigatrio")
    @Email(message = "O formato do e-mail  invlido")
    private String endereco;
}