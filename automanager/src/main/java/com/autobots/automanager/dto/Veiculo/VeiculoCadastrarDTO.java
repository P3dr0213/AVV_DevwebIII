package com.autobots.automanager.dto.Veiculo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import lombok.Data;

@Data
public class VeiculoCadastrarDTO {
    @NotNull(message = "O tipo do veculo  obrigatrio")
    private TipoVeiculo tipo;

    @NotBlank(message = "O modelo do veculo  obrigatrio")
    private String modelo;

    @NotBlank(message = "A placa do veculo  obrigatria")
    private String placa;
    
    // ID do proprietrio caso seja vinculado no cadastro
    private Long proprietarioId;
}