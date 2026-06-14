package com.autobots.automanager.dto.Empresa;

import java.util.Date;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.dto.Telefone.TelefoneExibirDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmpresaExibirDTO extends RepresentationModel<EmpresaExibirDTO> {
    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private Date cadastro;
    
    private EnderecoExibirDTO endereco;
    private List<TelefoneExibirDTO> telefones;
}