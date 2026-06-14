package com.autobots.automanager.modeladores;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.Cliente.ClienteExibirDTO;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class ClienteModelador implements RepresentationModelAssembler<ClienteExibirDTO, ClienteExibirDTO> {

    @Autowired
    private TelefoneModelador telefoneModelador;

    @Autowired
    private EnderecoModelador enderecoModelador;

    @Autowired
    private DocumentoModelador documentoModelador;

    @Autowired
    private AdicionadorLink<ClienteExibirDTO> adicionadorLink;

    @Override
    public ClienteExibirDTO toModel(ClienteExibirDTO clienteDto) {
        adicionadorLink.adicionarLink(clienteDto);

        if (clienteDto.getEndereco() != null) {
            clienteDto.setEndereco(enderecoModelador.toModel(clienteDto.getEndereco()));
        }

        if (clienteDto.getTelefones() != null) {
            clienteDto.setTelefones(clienteDto.getTelefones().stream()
                .map(telefoneModelador::toModel)
                .collect(Collectors.toList()));
        }

        if (clienteDto.getDocumentos() != null) {
            clienteDto.setDocumentos(clienteDto.getDocumentos().stream()
                .map(documentoModelador::toModel)
                .collect(Collectors.toList()));
        }

        return clienteDto;
    }
}