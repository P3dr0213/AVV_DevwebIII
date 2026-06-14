package com.autobots.automanager.dto.Telefone;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TelefoneAtualizadorDTO {
    private Long id;

    @NotBlank(message = "O DDD  obrigatrio")
    @Size(min = 2, max = 2, message = "O DDD deve conter exatamente 2 caracteres")
    private String ddd;

    @NotBlank(message = "O nmero do telefone  obrigatrio")
    @Size(min = 8, max = 9, message = "O nmero do telefone deve conter entre 8 e 9 caracteres")
    private String numero;
}
