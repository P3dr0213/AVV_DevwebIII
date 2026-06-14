package com.autobots.automanager.modelo.Documento;

import java.util.List;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class DocumentoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Documento documento, Documento atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getTipo())) {
				documento.setTipo(atualizacao.getTipo());
			}
			if (!verificador.verificar(atualizacao.getNumero())) {
				documento.setNumero(atualizacao.getNumero());
			}
		}
	}

	public void atualizar(List<Documento> documentos, List<Documento> atualizacoes) {
		if (atualizacoes == null || documentos == null) {
			return;
		}
		for (Documento atualizacao : atualizacoes) {
			for (Documento documento : documentos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId().equals(documento.getId())) {
						atualizar(documento, atualizacao);
					}
				}
			}
		}
	}
}
