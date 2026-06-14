package com.autobots.automanager.modelo.Cliente;

import org.springframework.stereotype.Component;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.StringVerificadorNulo;
import com.autobots.automanager.modelo.Telefone.TelefoneAtualizador;
import com.autobots.automanager.modelo.Documento.DocumentoAtualizador;
import com.autobots.automanager.modelo.Endereco.EnderecoAtualizador;

@Component
public class ClienteAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();

	private void atualizarDados(Cliente cliente, Cliente atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			cliente.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			cliente.setNomeSocial(atualizacao.getNomeSocial());
		}
		if (!(atualizacao.getDataCadastro() == null)) {
			cliente.setDataCadastro(atualizacao.getDataCadastro());
		}
		if (!(atualizacao.getDataNascimento() == null)) {
			cliente.setDataNascimento(atualizacao.getDataNascimento());
		}
	}

	public void atualizar(Cliente cliente, Cliente atualizacao) {
		if (atualizacao == null) {
			return;
		}
		atualizarDados(cliente, atualizacao);
		if (atualizacao.getEndereco() != null) {
			enderecoAtualizador.atualizar(cliente.getEndereco(), atualizacao.getEndereco());
		}
		documentoAtualizador.atualizar(cliente.getDocumentos(), atualizacao.getDocumentos());
		telefoneAtualizador.atualizar(cliente.getTelefones(), atualizacao.getTelefones());
	}
}
