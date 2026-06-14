package com.autobots.automanager.modeladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class EnderecoModelador implements RepresentationModelAssembler<EnderecoExibirDTO, EnderecoExibirDTO> {

    @Autowired
    private AdicionadorLink<EnderecoExibirDTO> adicionadorLink;

    @Override
    public EnderecoExibirDTO toModel(EnderecoExibirDTO enderecoDto) {
        adicionadorLink.adicionarLink(enderecoDto);
        return enderecoDto;
    }
}
