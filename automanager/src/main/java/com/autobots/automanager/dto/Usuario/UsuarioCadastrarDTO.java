package com.autobots.automanager.dto.Usuario;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRCadastrarDTO;
import com.autobots.automanager.dto.Documento.DocumentoCadastroDTO;
import com.autobots.automanager.dto.Email.EmailCadastroDTO;
import com.autobots.automanager.dto.Endereco.EnderecoCadastroDTO;
import com.autobots.automanager.dto.Telefone.TelefoneCadastroDTO;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Data;

@Data
public class UsuarioCadastrarDTO {

    @NotBlank(message = "O nome do usurio  obrigatrio")
    private String nome;

    private String nomeSocial;

    @NotEmpty(message = "O usurio deve ter pelo menos um perfil atribudo")
    private Set<PerfilUsuario> perfis;

    @Valid
    private EnderecoCadastroDTO endereco;

    @Valid
    private Set<TelefoneCadastroDTO> telefones;

    @Valid
    private Set<DocumentoCadastroDTO> documentos;

    @Valid
    private Set<EmailCadastroDTO> emails;

    @Valid
    private Set<CredencialQRCadastrarDTO> credenciais;
}