package com.autobots.automanager.dto.Veiculo;

import org.springframework.hateoas.RepresentationModel;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.autobots.automanager.dto.Usuario.UsuarioExibirDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VeiculoExibirDTO extends RepresentationModel<VeiculoExibirDTO> {
    private Long id;
    private TipoVeiculo tipo;
    private String modelo;
    private String placa;
    private UsuarioExibirDTO proprietario;
}