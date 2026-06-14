package com.autobots.automanager.dto.Servico;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ServicoCadastrarDTO {
    @NotBlank(message = "O nome do servio  obrigatrio")
    private String nome;

    @PositiveOrZero(message = "O valor do servio no pode ser negativo")
    private double valor;

    private String descricao;
}