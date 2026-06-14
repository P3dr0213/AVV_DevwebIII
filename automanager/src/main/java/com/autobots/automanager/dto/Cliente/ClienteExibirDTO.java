package com.autobots.automanager.dto.Cliente;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.dto.Documento.DocumentoExibirDTO;
import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.dto.Telefone.TelefoneExibirDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClienteExibirDTO extends RepresentationModel<ClienteExibirDTO> {
    private long id;
    private String nome;
    private String nomeSocial;
    private Date dataNascimento;
    private Date dataCadastro;

    private EnderecoExibirDTO endereco;
    private List<TelefoneExibirDTO> telefones;
    private List<DocumentoExibirDTO> documentos;
}
