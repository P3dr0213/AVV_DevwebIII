package com.autobots.automanager.modelo.Empresa;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelo.StringVerificadorNulo;
import com.autobots.automanager.modelo.Endereco.EnderecoAtualizador;
import com.autobots.automanager.modelo.Telefone.TelefoneAtualizador;

@Component
public class EmpresaAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();

	private void atualizarDados(Empresa empresa, Empresa atualizacao) {
		if (!verificador.verificar(atualizacao.getRazaoSocial())) {
			empresa.setRazaoSocial(atualizacao.getRazaoSocial());
		}
		if (!verificador.verificar(atualizacao.getNomeFantasia())) {
			empresa.setNomeFantasia(atualizacao.getNomeFantasia());
		}
		if (atualizacao.getCadastro() != null) {
			empresa.setCadastro(atualizacao.getCadastro());
		}
	}

	public void atualizar(Empresa empresa, Empresa atualizacao) {
		atualizarDados(empresa, atualizacao);
		if (empresa.getEndereco() != null && atualizacao.getEndereco() != null) {
			enderecoAtualizador.atualizar(empresa.getEndereco(), atualizacao.getEndereco());
		}
		telefoneAtualizador.atualizar(new ArrayList<>(empresa.getTelefones()), new ArrayList<>(atualizacao.getTelefones()));
	}
}