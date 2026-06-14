package com.autobots.automanager.dto.Documento;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DocumentoCadastroDTO {

    private Long idCliente;

    @NotBlank(message = "O tipo do documento  obrigatrio")
    private String tipo;

    @NotBlank(message = "O nmero do documento  obrigatrio")
    private String numero;
}