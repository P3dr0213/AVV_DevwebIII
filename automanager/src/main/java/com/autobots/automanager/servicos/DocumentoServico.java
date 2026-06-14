package com.autobots.automanager.servicos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Documento.DocumentoAtualizadorDTO;
import com.autobots.automanager.dto.Documento.DocumentoCadastroDTO;
import com.autobots.automanager.dto.Documento.DocumentoExibirDTO;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;

@Service
public class DocumentoServico {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<DocumentoExibirDTO> buscarTodos() {

        Map<Documento, Cliente> documentoPorCliente = new java.util.HashMap<>();
        List<Cliente> clientes = clienteRepositorio.findAll();

        for (Cliente cliente : clientes) {
            for (Documento documento : cliente.getDocumentos()) {
                documentoPorCliente.put(documento, cliente);
            }
        }

        return documentoPorCliente.entrySet().stream()
                .map(entry -> converterParaExibirDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public DocumentoExibirDTO buscarPorId(Long id) {
        Documento documento = documentoRepositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Documento no encontrado"));

        Cliente cliente = clienteRepositorio.findAll().stream()
                .filter(c -> c.getDocumentos().stream()
                        .anyMatch(d -> d.getId().equals(id)))
                .findFirst()
                .orElse(null);

        return converterParaExibirDTO(documento, cliente);
    }

    public DocumentoExibirDTO cadastrarPorId(DocumentoCadastroDTO dto) {
        if (dto.getIdCliente() == null) {
            throw new EntidadeNaoEncontradaException("O id do cliente  obrigatrio");
        }
        Cliente cliente = clienteRepositorio.findById(dto.getIdCliente())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente no encontrado"));

        Documento documento = new Documento();

        documento.setTipo(dto.getTipo());
        documento.setNumero(dto.getNumero());

        cliente.getDocumentos().add(documento);

        Cliente clienteSalvo = clienteRepositorio.save(cliente);

        Documento documentoPersistido = clienteSalvo.getDocumentos().stream()
                .filter(d -> d.getNumero().equals(dto.getNumero())
                        && d.getTipo().equals(dto.getTipo()))
                .findFirst()
                .orElse(documento);

        return converterParaExibirDTO(documentoPersistido, clienteSalvo);
    }

    public DocumentoExibirDTO atualizarPorId(DocumentoAtualizadorDTO dto) {
        Documento documento = documentoRepositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Documento no encontrado"));

        documento.setTipo(dto.getTipo());
        documento.setNumero(dto.getNumero());

        final Documento documentoPersistido = documentoRepositorio.save(documento);

        Cliente cliente = clienteRepositorio.findAll().stream()
                .filter(c -> c.getDocumentos().stream()
                        .anyMatch(d -> d.getId().equals(documentoPersistido.getId())))
                .findFirst()
                .orElse(null);

        return converterParaExibirDTO(documentoPersistido, cliente);
    }

    public void excluir(Long id) {
        Cliente cliente = clienteRepositorio.findAll().stream()
                .filter(c -> c.getDocumentos().stream()
                        .anyMatch(d -> d.getId().equals(id)))
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Documento no encontrado"));

        boolean removido = cliente.getDocumentos()
                .removeIf(d -> d.getId().equals(id));

        if (!removido) {
            throw new EntidadeNaoEncontradaException("Documento no encontrado");
        }
        clienteRepositorio.save(cliente);
    }

    private DocumentoExibirDTO converterParaExibirDTO(Documento documento, Cliente cliente) {

        DocumentoExibirDTO dto = new DocumentoExibirDTO();

        dto.setId(documento.getId());
        dto.setTipo(documento.getTipo());
        dto.setNumero(documento.getNumero());

        if (cliente != null) {
            dto.setIdCliente(cliente.getId());
        }

        return dto;
    }
}