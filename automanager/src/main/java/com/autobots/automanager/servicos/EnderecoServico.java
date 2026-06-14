package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Endereco.EnderecoAtualizadorDTO;
import com.autobots.automanager.dto.Endereco.EnderecoCadastroDTO;
import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;

@Service
public class EnderecoServico {

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<EnderecoExibirDTO> buscarTodos() {

        List<Cliente> clientes = clienteRepositorio.findAll();

        return clientes.stream()
                .filter(cliente -> cliente.getEndereco() != null)
                .map(cliente -> converterParaExibirDTO(cliente.getEndereco(), cliente))
                .collect(Collectors.toList());
    }

    public EnderecoExibirDTO buscarPorId(Long id) {
        Endereco endereco = enderecoRepositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereo no encontrado"));

        Cliente cliente = clienteRepositorio.findAll().stream()
                .filter(c -> c.getEndereco() != null
                        && c.getEndereco().getId().equals(id))
                .findFirst()
                .orElse(null);

        return converterParaExibirDTO(endereco, cliente);
    }

    public EnderecoExibirDTO cadastrar(EnderecoCadastroDTO dto) {
        if (dto.getIdCliente() == null) {
            throw new EntidadeNaoEncontradaException("O id do cliente  obrigatrio");
        }
        Cliente cliente = clienteRepositorio.findById(dto.getIdCliente())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente no encontrado"));

        Endereco endereco = new Endereco();

        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setCodigoPostal(dto.getCep());
        endereco.setInformacoesAdicionais(dto.getInformacoesAdicionais());

        cliente.setEndereco(endereco);

        Cliente clienteSalvo = clienteRepositorio.save(cliente);

        Endereco enderecoPersistido = clienteSalvo.getEndereco();

        return converterParaExibirDTO(enderecoPersistido, clienteSalvo);
    }

    public EnderecoExibirDTO atualizarporId(EnderecoAtualizadorDTO dto) {
        Endereco endereco = enderecoRepositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereo no encontrado"));

        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setCodigoPostal(dto.getCep());
        endereco.setInformacoesAdicionais(dto.getInformacoesAdicionais());

        final Endereco enderecoPersistido = enderecoRepositorio.save(endereco);

        Cliente cliente = clienteRepositorio.findAll().stream()
                .filter(c -> c.getEndereco() != null
                        && c.getEndereco().getId().equals(enderecoPersistido.getId()))
                .findFirst()
                .orElse(null);

        return converterParaExibirDTO(enderecoPersistido, cliente);
    }

    public void excluir(Long id) {
        Cliente cliente = clienteRepositorio.findAll().stream()
                .filter(c -> c.getEndereco() != null
                        && c.getEndereco().getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereo no encontrado"));

        cliente.setEndereco(null);

        clienteRepositorio.save(cliente);
    }

    private EnderecoExibirDTO converterParaExibirDTO(Endereco endereco, Cliente cliente) {

        EnderecoExibirDTO dto = new EnderecoExibirDTO();

        dto.setId(endereco.getId());
        dto.setEstado(endereco.getEstado());
        dto.setCidade(endereco.getCidade());
        dto.setBairro(endereco.getBairro());
        dto.setRua(endereco.getRua());
        dto.setNumero(endereco.getNumero());
        dto.setCep(endereco.getCodigoPostal());
        dto.setInformacoesAdicionais(endereco.getInformacoesAdicionais());

        if (cliente != null) {
            dto.setIdCliente(cliente.getId());
        }

        return dto;
    }
}