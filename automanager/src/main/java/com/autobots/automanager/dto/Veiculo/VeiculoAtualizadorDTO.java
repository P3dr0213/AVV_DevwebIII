package com.autobots.automanager.dto.Veiculo;

import javax.validation.constraints.NotNull;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import lombok.Data;

@Data
public class VeiculoAtualizadorDTO {
    @NotNull(message = "O id do veculo  obrigatrio")
    private Long id;

    private TipoVeiculo tipo;
    private String modelo;
    private String placa;
    private Long proprietarioId;
}