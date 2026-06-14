package com.autobots.automanager.dto.Venda;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VendaCadastrarDTO {
    @NotBlank(message = "A identificao da venda  obrigatria")
    private String identificacao;

    @NotNull(message = "O ID do cliente  obrigatrio")
    private Long clienteId;

    @NotNull(message = "O ID do funcionrio  obrigatrio")
    private Long funcionarioId;

    private Long veiculoId;
    private Set<Long> mercadoriasIds;
    private Set<Long> servicosIds;
}