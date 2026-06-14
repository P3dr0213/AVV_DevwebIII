package com.autobots.automanager.servicos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.Telefone.TelefoneAtualizadorDTO;
import com.autobots.automanager.dto.Telefone.TelefoneCadastroDTO;
import com.autobots.automanager.dto.Telefone.TelefoneExibirDTO;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;

@Service
public class TelefoneServico {

    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<TelefoneExibirDTO> buscarTodos() {

        Map<Telefone, Cliente> telefonePorCliente = new java.util.HashMap<>();
        List<Cliente> clientes = clienteRepositorio.findAll();

        for (Cliente cliente : clientes) {
            for (Telefone telefone : cliente.getTelefones()) {
                telefonePorCliente.put(telefone, cliente);
            }
        }

        return telefonePorCliente.entrySet().stream()
                .map(entry -> converterParaExibirDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public TelefoneExibirDTO buscarPorId(Long id) {
        Telefone telefone = telefoneRepositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Telefone no encontrado"));

        Cliente cliente = clienteRepositorio.findAll().stream()
                .filter(c -> c.getTelefones().stream()
                        .anyMatch(t -> t.getId().equals(id)))
                .findFirst()
                .orElse(null);

        return converterParaExibirDTO(telefone, cliente);
    }

    public TelefoneExibirDTO cadastrar(TelefoneCadastroDTO dto) {
        if (dto.getIdCliente() == null) {
            throw new EntidadeNaoEncontradaException("O id do cliente  obrigatrio");
        }
        Cliente cliente = clienteRepositorio.findById(dto.getIdCliente())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente no encontrado"));

        Telefone telefone = new Telefone();
        telefone.setDdd(dto.getDdd());
        telefone.setNumero(dto.getNumero());

        cliente.getTelefones().add(telefone);

        Cliente clienteSalvo = clienteRepositorio.save(cliente);

        Telefone telefonePersistido = clienteSalvo.getTelefones().stream()
                .filter(t -> t != null
                        && java.util.Objects.equals(t.getNumero(), dto.getNumero())
                        && java.util.Objects.equals(t.getDdd(), dto.getDdd()))
                .findFirst()
                .orElse(telefone);

        return converterParaExibirDTO(telefonePersistido, clienteSalvo);
    }

    public TelefoneExibirDTO atualizar(TelefoneAtualizadorDTO dto) {
        Telefone telefone = telefoneRepositorio.findById(dto.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Telefone no encontrado"));

        telefone.setDdd(dto.getDdd());
        telefone.setNumero(dto.getNumero());

        final Telefone telefonePersistido = telefoneRepositorio.save(telefone);

        Cliente cliente = clienteRepositorio.findAll().stream()
                .filter(c -> c.getTelefones().stream()
                        .anyMatch(t -> t.getId().equals(telefonePersistido.getId())))
                .findFirst()
                .orElse(null);

        return converterParaExibirDTO(telefonePersistido, cliente);
    }

    public void excluir(Long id) {
        Cliente cliente = clienteRepositorio.findAll().stream()
                .filter(c -> c.getTelefones().stream()
                        .anyMatch(t -> t.getId().equals(id)))
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Telefone no encontrado"));

        Telefone telefone = cliente.getTelefones().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Telefone no encontrado"));

        cliente.getTelefones().remove(telefone);

        clienteRepositorio.save(cliente);
    }

    private TelefoneExibirDTO converterParaExibirDTO(Telefone telefone, Cliente cliente) {
        TelefoneExibirDTO dto = new TelefoneExibirDTO();

        dto.setId(telefone.getId());
        dto.setDdd(telefone.getDdd());
        dto.setNumero(telefone.getNumero());

        if (cliente != null) {
            dto.setIdCliente(cliente.getId());
        }

        return dto;
    }
}