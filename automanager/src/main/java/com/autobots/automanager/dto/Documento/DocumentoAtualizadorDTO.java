package com.autobots.automanager.dto.Documento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DocumentoAtualizadorDTO {
    @NotNull(message = "O id do documento  obrigatrio")
    private Long id;

    @NotBlank(message = "O tipo do documento  obrigatrio")
    private String tipo;

    @NotBlank(message = "O nmero do documento  obrigatrio")
    private String numero;
}
