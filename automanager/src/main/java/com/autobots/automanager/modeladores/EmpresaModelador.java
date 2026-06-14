package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Empresa.EmpresaExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class EmpresaModelador implements RepresentationModelAssembler<EmpresaExibirDTO, EmpresaExibirDTO> {

    @Autowired
    private AdicionadorLink<EmpresaExibirDTO> adicionadorLink;

    @Autowired
    private TelefoneModelador telefoneModelador;

    @Autowired
    private EnderecoModelador enderecoModelador;

    @Override
    public EmpresaExibirDTO toModel(EmpresaExibirDTO dto) {
        adicionadorLink.adicionarLink(dto);
        
        if (dto.getEndereco() != null) {
            dto.setEndereco(enderecoModelador.toModel(dto.getEndereco()));
        }
        
        if (dto.getTelefones() != null) {
            dto.getTelefones().forEach(telefoneModelador::toModel);
        }
        
        return dto;
    }
}