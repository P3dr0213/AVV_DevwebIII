package com.autobots.automanager.dto.CredencialCodigoBarra;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CredencialQRCadastrarDTO {
    @NotNull(message = "O cdigo de barras  obrigatrio para o cadastro")
    private Long codigo;

    @NotNull(message = "O status de atividade (inativo) deve ser informado")
    private Boolean inativo;
}
