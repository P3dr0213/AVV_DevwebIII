package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Veiculo.VeiculoExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class VeiculoModelador implements RepresentationModelAssembler<VeiculoExibirDTO, VeiculoExibirDTO> {

    @Autowired
    private AdicionadorLink<VeiculoExibirDTO> adicionadorLink;

    @Autowired
    private UsuarioModelador usuarioModelador;

    @Override
    public VeiculoExibirDTO toModel(VeiculoExibirDTO dto) {
        adicionadorLink.adicionarLink(dto);
        
        if (dto.getProprietario() != null) {
            dto.setProprietario(usuarioModelador.toModel(dto.getProprietario()));
        }
        
        return dto;
    }
}