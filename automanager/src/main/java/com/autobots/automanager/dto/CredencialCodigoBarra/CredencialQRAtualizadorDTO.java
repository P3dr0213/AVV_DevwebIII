package com.autobots.automanager.dto.CredencialCodigoBarra;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CredencialQRAtualizadorDTO {
    @NotNull(message = "O id da credencial  obrigatrio para atualizao")
    private Long id;

    @NotNull(message = "O cdigo de barras  obrigatrio")
    private Long codigo;

    @NotNull(message = "O status de atividade (inativo) deve ser informado")
    private Boolean inativo;
}
