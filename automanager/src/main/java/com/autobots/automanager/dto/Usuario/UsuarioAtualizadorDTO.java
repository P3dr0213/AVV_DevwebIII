package com.autobots.automanager.dto.Usuario;

import java.util.Set;
import javax.validation.constraints.NotNull;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import lombok.Data;

@Data
public class UsuarioAtualizadorDTO {
    @NotNull(message = "O id do usurio  obrigatrio")
    private Long id;

    private String nome;
    private String nomeSocial;
    private Set<PerfilUsuario> perfis;
}