package com.autobots.automanager.modelo.CredencialCodigoBarra;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entidades.CredencialCodigoBarra;

@Component
public class CredencialCodigoBarraSelecionador {
    
    public CredencialCodigoBarra selecionar(List<CredencialCodigoBarra> credenciais, long id) {
        CredencialCodigoBarra selecionado = null;
        for (CredencialCodigoBarra credencial : credenciais) {
            if (credencial.getId() == id) {
                selecionado = credencial;
            }
        }
        return selecionado;
    }
}