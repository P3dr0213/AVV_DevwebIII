package com.autobots.automanager.modelo.CredencialCodigoBarra;

import org.springframework.stereotype.Component;
import com.autobots.automanager.entidades.CredencialCodigoBarra;

@Component
public class CredencialCodigoBarraAtualizador {

    public void atualizar(CredencialCodigoBarra credencial, CredencialCodigoBarra atualizacao) {
        if (atualizacao != null) {
            if (atualizacao.getCodigo() != 0) {
                credencial.setCodigo(atualizacao.getCodigo());
            }
            if (atualizacao.isInativo()) {
                credencial.setInativo(true);
            }
            if (atualizacao.getUltimoAcesso() != null) {
                credencial.setUltimoAcesso(atualizacao.getUltimoAcesso());
            }
        }
    }
}