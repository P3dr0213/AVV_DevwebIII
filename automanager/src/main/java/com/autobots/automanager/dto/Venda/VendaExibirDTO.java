package com.autobots.automanager.dto.Venda;

import java.util.Date;
import java.util.Set;
import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.dto.Usuario.UsuarioExibirDTO;
import com.autobots.automanager.dto.Mercadoria.MercadoriaExibirDTO;
import com.autobots.automanager.dto.Servico.ServicoExibirDTO;
import com.autobots.automanager.dto.Veiculo.VeiculoExibirDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VendaExibirDTO extends RepresentationModel<VendaExibirDTO> {
    private Long id;
    private Date cadastro;
    private String identificacao;
    private UsuarioExibirDTO cliente;
    private UsuarioExibirDTO funcionario;
    private VeiculoExibirDTO veiculo;
    private Set<MercadoriaExibirDTO> mercadorias;
    private Set<ServicoExibirDTO> servicos;
}