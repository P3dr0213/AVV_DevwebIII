package com.autobots.automanager.modelo.CredencialUsuarioSenha;

import org.springframework.stereotype.Component;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.modelo.StringVerificadorNulo;

@Component
public class CredencialUsuarioSenhaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(CredencialUsuarioSenha credencial, CredencialUsuarioSenha atualizacao) {
        if (atualizacao != null) {
            if (!verificador.verificar(atualizacao.getNomeUsuario())) {
                credencial.setNomeUsuario(atualizacao.getNomeUsuario());
            }
            if (!verificador.verificar(atualizacao.getSenha())) {
                credencial.setSenha(atualizacao.getSenha());
            }
            credencial.setInativo(atualizacao.isInativo());
            if (atualizacao.getUltimoAcesso() != null) {
                credencial.setUltimoAcesso(atualizacao.getUltimoAcesso());
            }
        }
    }
}