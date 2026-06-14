package com.autobots.automanager.dto.Venda;

import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VendaAtualizadorDTO {
    @NotNull(message = "O id da venda  obrigatrio")
    private Long id;

    private String identificacao;
    private Long clienteId;
    private Long funcionarioId;
    private Long veiculoId;
    private Set<Long> mercadoriasIds;
    private Set<Long> servicosIds;
}