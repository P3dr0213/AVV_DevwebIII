package com.autobots.automanager.dto.Usuario;

import java.util.Set;
import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.dto.Mercadoria.MercadoriaExibirDTO;
import com.autobots.automanager.dto.Telefone.TelefoneExibirDTO;
import com.autobots.automanager.dto.Veiculo.VeiculoExibirDTO;
import com.autobots.automanager.dto.Venda.VendaExibirDTO;
import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRExibirDTO;
import com.autobots.automanager.dto.Documento.DocumentoExibirDTO;
import com.autobots.automanager.dto.Email.EmailExibirDTO;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UsuarioExibirDTO extends RepresentationModel<UsuarioExibirDTO> {
    private Long id;
    private String nome;
    private String nomeSocial;
    private Set<PerfilUsuario> perfis;
    private EnderecoExibirDTO endereco;
    private Set<TelefoneExibirDTO> telefones;
    private Set<DocumentoExibirDTO> documentos;
    private Set<EmailExibirDTO> emails;
    private Set<CredencialQRExibirDTO> credenciaisCodigoBarra;
    private Set<MercadoriaExibirDTO> mercadorias;
    private Set<VendaExibirDTO> vendas;
    private Set<VeiculoExibirDTO> veiculos;
}