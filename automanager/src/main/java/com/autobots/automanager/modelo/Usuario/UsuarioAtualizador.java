package com.autobots.automanager.modelo.Usuario;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.StringVerificadorNulo;
import com.autobots.automanager.modelo.Endereco.EnderecoAtualizador;
import com.autobots.automanager.modelo.Documento.DocumentoAtualizador;
import com.autobots.automanager.modelo.Telefone.TelefoneAtualizador;
import com.autobots.automanager.modelo.Email.EmailAtualizador;

@Component
public class UsuarioAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private EmailAtualizador emailAtualizador = new EmailAtualizador();

	private void atualizarDados(Usuario usuario, Usuario atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			usuario.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			usuario.setNomeSocial(atualizacao.getNomeSocial());
		}
		if (atualizacao.getPerfis() != null && !atualizacao.getPerfis().isEmpty()) {
			usuario.setPerfis(atualizacao.getPerfis());
		}
	}

	public void atualizar(Usuario usuario, Usuario atualizacao) {
		atualizarDados(usuario, atualizacao);
		if (usuario.getEndereco() != null && atualizacao.getEndereco() != null) {
			enderecoAtualizador.atualizar(usuario.getEndereco(), atualizacao.getEndereco());
		}
		documentoAtualizador.atualizar(new ArrayList<>(usuario.getDocumentos()), new ArrayList<>(atualizacao.getDocumentos()));
		telefoneAtualizador.atualizar(new ArrayList<>(usuario.getTelefones()), new ArrayList<>(atualizacao.getTelefones()));
		emailAtualizador.atualizar(new ArrayList<>(usuario.getEmails()), new ArrayList<>(atualizacao.getEmails()));
	}
}