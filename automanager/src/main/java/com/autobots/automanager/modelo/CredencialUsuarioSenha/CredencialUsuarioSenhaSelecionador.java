package com.autobots.automanager.modelo.CredencialUsuarioSenha;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;

@Component
public class CredencialUsuarioSenhaSelecionador {
    
    public CredencialUsuarioSenha selecionar(List<CredencialUsuarioSenha> credenciais, long id) {
        CredencialUsuarioSenha selecionado = null;
        for (CredencialUsuarioSenha credencial : credenciais) {
            if (credencial.getId() == id) {
                selecionado = credencial;
            }
        }
        return selecionado;
    }
}