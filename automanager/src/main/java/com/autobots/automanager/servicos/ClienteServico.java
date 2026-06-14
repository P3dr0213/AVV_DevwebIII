package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Cliente.ClienteAtualizadorDTO;
import com.autobots.automanager.dto.Cliente.ClienteCadastrarDTO;
import com.autobots.automanager.dto.Cliente.ClienteExibirDTO;
import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.dto.Documento.DocumentoExibirDTO;
import com.autobots.automanager.dto.Telefone.TelefoneExibirDTO;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.Cliente.ClienteAtualizador;
import com.autobots.automanager.modelo.Cliente.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio repositorio;

    @Autowired
    private ClienteSelecionador selecionador;

    @Autowired
    private ClienteAtualizador atualizador;

    public void cadastrar(Cliente cliente) {
        repositorio.save(cliente);
    }

    public List<ClienteExibirDTO> buscarTodos() {
        List<Cliente> clientes = repositorio.findAll();
        return clientes.stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public ClienteExibirDTO buscarPorIdDTO(Long id) {
        Cliente cliente = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente no encontrado"));
        return converterParaExibirDTO(cliente);
    }

    public void atualizar(Cliente atualizacao) {
        Cliente cliente = repositorio.findById(atualizacao.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente no encontrado"));
        atualizador.atualizar(cliente, atualizacao);
        repositorio.save(cliente);
    }

    public void excluir(Long id) {
        Cliente cliente = repositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente no encontrado"));
        repositorio.delete(cliente);
    }

    public ClienteExibirDTO cadastrarViaDTO(ClienteCadastrarDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setNomeSocial(dto.getNomeSocial());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setDataCadastro(new java.util.Date()); 
        
        this.cadastrar(cliente);
        return converterParaExibirDTO(cliente);
    }

    private ClienteExibirDTO converterParaExibirDTO(Cliente cliente) {
        ClienteExibirDTO dto = new ClienteExibirDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setNomeSocial(cliente.getNomeSocial());
        dto.setDataNascimento(cliente.getDataNascimento());
        dto.setDataCadastro(cliente.getDataCadastro());

        // 1. Converter Endereco (Passando o cliente)
        if (cliente.getEndereco() != null) {
            dto.setEndereco(converterEnderecoParaDTO(cliente.getEndereco(), cliente));
        }

        // 2. Converter Telefones (Passando o cliente via Lambda)
        if (cliente.getTelefones() != null) {
            dto.setTelefones(cliente.getTelefones().stream()
                    .map(tel -> converterTelefoneParaDTO(tel, cliente)) 
                    .collect(Collectors.toList()));
        }

        // 3. Converter Documentos (Passando o cliente via Lambda)
        if (cliente.getDocumentos() != null) {
            dto.setDocumentos(cliente.getDocumentos().stream()
                    .map(doc -> converterDocumentoParaDTO(doc, cliente))
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private EnderecoExibirDTO converterEnderecoParaDTO(Endereco endereco, Cliente cliente) {
        EnderecoExibirDTO dto = new EnderecoExibirDTO();
        dto.setId(endereco.getId());
        dto.setEstado(endereco.getEstado());
        dto.setCidade(endereco.getCidade());
        dto.setBairro(endereco.getBairro());
        dto.setRua(endereco.getRua());
        dto.setNumero(endereco.getNumero());
        dto.setCep(endereco.getCodigoPostal());
        dto.setInformacoesAdicionais(endereco.getInformacoesAdicionais());
        dto.setIdCliente(cliente.getId());
        return dto;
    }

    private TelefoneExibirDTO converterTelefoneParaDTO(Telefone telefone, Cliente cliente) {
        TelefoneExibirDTO dto = new TelefoneExibirDTO();
        dto.setId(telefone.getId());
        dto.setDdd(telefone.getDdd());
        dto.setNumero(telefone.getNumero());
        dto.setIdCliente(cliente.getId());
        return dto;
    }

    private DocumentoExibirDTO converterDocumentoParaDTO(Documento documento, Cliente cliente) {
        DocumentoExibirDTO dto = new DocumentoExibirDTO();
        dto.setId(documento.getId());
        dto.setTipo(documento.getTipo());
        dto.setNumero(documento.getNumero());
        dto.setIdCliente(cliente.getId());
        return dto;
    }

    public void atualizarViaDTO(ClienteAtualizadorDTO dto) {
        Cliente cliente = repositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente no encontrado"));

        Cliente dadosAtualizacao = new Cliente();
        dadosAtualizacao.setNome(dto.getNome());
        dadosAtualizacao.setNomeSocial(dto.getNomeSocial());
        dadosAtualizacao.setDataNascimento(dto.getDataNascimento());

        atualizador.atualizar(cliente, dadosAtualizacao);

        repositorio.save(cliente);
    }

}