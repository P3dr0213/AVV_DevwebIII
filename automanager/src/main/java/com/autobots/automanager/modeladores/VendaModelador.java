package com.autobots.automanager.modeladores;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Venda.VendaExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class VendaModelador implements RepresentationModelAssembler<VendaExibirDTO, VendaExibirDTO> {

    @Autowired
    private AdicionadorLink<VendaExibirDTO> adicionadorLink;

    @Autowired
    private UsuarioModelador usuarioModelador;

    @Autowired
    private VeiculoModelador veiculoModelador;

    @Autowired
    private MercadoriaModelador mercadoriaModelador;

    @Autowired
    private ServicoModelador servicoModelador;

    @Override
    public VendaExibirDTO toModel(VendaExibirDTO dto) {
        adicionadorLink.adicionarLink(dto);

        if (dto.getCliente() != null) {
            dto.setCliente(usuarioModelador.toModel(dto.getCliente()));
        }
        
        if (dto.getFuncionario() != null) {
            dto.setFuncionario(usuarioModelador.toModel(dto.getFuncionario()));
        }

        if (dto.getVeiculo() != null) {
            dto.setVeiculo(veiculoModelador.toModel(dto.getVeiculo()));
        }

        if (dto.getMercadorias() != null) {
            dto.setMercadorias(dto.getMercadorias().stream()
                .map(mercadoriaModelador::toModel)
                .collect(Collectors.toSet()));
        }

        if (dto.getServicos() != null) {
            dto.setServicos(dto.getServicos().stream()
                .map(servicoModelador::toModel)
                .collect(Collectors.toSet()));
        }

        return dto;
    }
}