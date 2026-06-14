package com.autobots.automanager.servicos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.CredencialCodigoBarra.CredencialQRExibirDTO;
import com.autobots.automanager.dto.Documento.DocumentoExibirDTO;
import com.autobots.automanager.dto.Email.EmailExibirDTO;
import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.dto.Mercadoria.MercadoriaExibirDTO;
import com.autobots.automanager.dto.Telefone.TelefoneExibirDTO;
import com.autobots.automanager.dto.Usuario.UsuarioAtualizadorDTO;
import com.autobots.automanager.dto.Usuario.UsuarioCadastrarDTO;
import com.autobots.automanager.dto.Usuario.UsuarioExibirDTO;
import com.autobots.automanager.dto.Veiculo.VeiculoExibirDTO;
import com.autobots.automanager.dto.Venda.VendaExibirDTO;
import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;

    public List<UsuarioExibirDTO> buscarTodos() {
        return repositorio.findAll()
                .stream()
                .map(this::converterParaExibirDTO)
                .collect(Collectors.toList());
    }

    public UsuarioExibirDTO buscarPorIdDTO(Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() ->
                        new EntidadeNaoEncontradaException("Usurio no encontrado"));

        return converterParaExibirDTO(usuario);
    }

    public UsuarioExibirDTO cadastrar(UsuarioCadastrarDTO dto) {

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setNomeSocial(dto.getNomeSocial());

        if (dto.getPerfis() != null) {
            usuario.getPerfis().addAll(dto.getPerfis());
        }

        // ENDEREO
        if (dto.getEndereco() != null) {

            Endereco endereco = new Endereco();

            endereco.setEstado(dto.getEndereco().getEstado());
            endereco.setCidade(dto.getEndereco().getCidade());
            endereco.setBairro(dto.getEndereco().getBairro());
            endereco.setRua(dto.getEndereco().getRua());
            endereco.setNumero(dto.getEndereco().getNumero());
            endereco.setCodigoPostal(dto.getEndereco().getCep());
            endereco.setInformacoesAdicionais(
                    dto.getEndereco().getInformacoesAdicionais());

            usuario.setEndereco(endereco);
        }

        // TELEFONES
        if (dto.getTelefones() != null) {

            dto.getTelefones().forEach(t -> {

                Telefone telefone = new Telefone();

                telefone.setDdd(t.getDdd());
                telefone.setNumero(t.getNumero());

                usuario.getTelefones().add(telefone);
            });
        }

        // DOCUMENTOS
        if (dto.getDocumentos() != null) {

            dto.getDocumentos().forEach(d -> {

                Documento documento = new Documento();

                documento.setTipo(d.getTipo());
                documento.setNumero(d.getNumero());

                usuario.getDocumentos().add(documento);
            });
        }

        // EMAILS
        if (dto.getEmails() != null) {

            dto.getEmails().forEach(e -> {

                Email email = new Email();

                email.setEndereco(e.getEndereco());

                usuario.getEmails().add(email);
            });
        }

        // CREDENCIAIS
        if (dto.getCredenciais() != null) {

            dto.getCredenciais().forEach(c -> {

                CredencialCodigoBarra credencial =
                        new CredencialCodigoBarra();

                credencial.setCodigo(c.getCodigo());
                credencial.setInativo(c.getInativo());
                credencial.setCriacao(new java.util.Date());

                usuario.getCredenciais().add(credencial);
            });
        }

        Usuario usuarioSalvo = repositorio.save(usuario);

        return converterParaExibirDTO(usuarioSalvo);
    }

    public void atualizar(UsuarioAtualizadorDTO dto) {

        Usuario usuario = repositorio.findById(dto.getId())
                .orElseThrow(() ->
                        new EntidadeNaoEncontradaException("Usurio no encontrado"));

        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }

        if (dto.getNomeSocial() != null) {
            usuario.setNomeSocial(dto.getNomeSocial());
        }

        if (dto.getPerfis() != null && !dto.getPerfis().isEmpty()) {

            usuario.getPerfis().clear();
            usuario.getPerfis().addAll(dto.getPerfis());
        }

        repositorio.save(usuario);
    }

    public void excluir(Long id) {

        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() ->
                        new EntidadeNaoEncontradaException("Usurio no encontrado"));

        repositorio.delete(usuario);
    }

    public UsuarioExibirDTO vincularMercadoria(Long usuarioId, Long mercadoriaId) {
        Usuario usuario = repositorio.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));

        Mercadoria mercadoria = mercadoriaRepositorio.findById(mercadoriaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Mercadoria não encontrada"));

        usuario.getMercadorias().add(mercadoria);
        repositorio.save(usuario);

        return converterParaExibirDTO(usuario);
    }

    private UsuarioExibirDTO converterParaExibirDTO(Usuario entidade) {

        UsuarioExibirDTO dto = new UsuarioExibirDTO();

        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setNomeSocial(entidade.getNomeSocial());
        dto.setPerfis(entidade.getPerfis());

        // ENDEREO
        if (entidade.getEndereco() != null) {

            EnderecoExibirDTO enderecoDTO =
                    new EnderecoExibirDTO();

            enderecoDTO.setId(entidade.getEndereco().getId());
            enderecoDTO.setEstado(entidade.getEndereco().getEstado());
            enderecoDTO.setCidade(entidade.getEndereco().getCidade());
            enderecoDTO.setBairro(entidade.getEndereco().getBairro());
            enderecoDTO.setRua(entidade.getEndereco().getRua());
            enderecoDTO.setNumero(entidade.getEndereco().getNumero());
            enderecoDTO.setCep(entidade.getEndereco().getCodigoPostal());
            enderecoDTO.setInformacoesAdicionais(
                    entidade.getEndereco().getInformacoesAdicionais());

            enderecoDTO.setIdCliente(entidade.getId());

            dto.setEndereco(enderecoDTO);
        }

        // TELEFONES
        dto.setTelefones(
                entidade.getTelefones()
                        .stream()
                        .map(t -> {

                            TelefoneExibirDTO telefoneDTO =
                                    new TelefoneExibirDTO();

                            telefoneDTO.setId(t.getId());
                            telefoneDTO.setDdd(t.getDdd());
                            telefoneDTO.setNumero(t.getNumero());
                            telefoneDTO.setIdCliente(entidade.getId());

                            return telefoneDTO;
                        })
                        .collect(Collectors.toSet())
        );

        // DOCUMENTOS
        dto.setDocumentos(
                entidade.getDocumentos()
                        .stream()
                        .map(d -> {

                            DocumentoExibirDTO documentoDTO =
                                    new DocumentoExibirDTO();

                            documentoDTO.setId(d.getId());
                            documentoDTO.setTipo(d.getTipo());
                            documentoDTO.setNumero(d.getNumero());
                            documentoDTO.setIdCliente(entidade.getId());

                            return documentoDTO;
                        })
                        .collect(Collectors.toSet())
        );

        // EMAILS
        dto.setEmails(
                entidade.getEmails()
                        .stream()
                        .map(e -> {

                            EmailExibirDTO emailDTO =
                                    new EmailExibirDTO();

                            emailDTO.setId(e.getId());
                            emailDTO.setEndereco(e.getEndereco());

                            return emailDTO;
                        })
                        .collect(Collectors.toSet())
        );

        // CREDENCIAIS
        dto.setCredenciaisCodigoBarra(
                entidade.getCredenciais()
                        .stream()
                        .filter(c -> c instanceof CredencialCodigoBarra)
                        .map(c -> {

                            CredencialCodigoBarra cred =
                                    (CredencialCodigoBarra) c;

                            CredencialQRExibirDTO credDTO =
                                    new CredencialQRExibirDTO();

                            credDTO.setId(cred.getId());
                            credDTO.setCodigo(cred.getCodigo());
                            credDTO.setInativo(cred.isInativo());

                            return credDTO;
                        })
                        .collect(Collectors.toSet())
        );

        // MERCADORIAS
        dto.setMercadorias(
                entidade.getMercadorias()
                        .stream()
                        .map(m -> {

                            MercadoriaExibirDTO mercDTO =
                                    new MercadoriaExibirDTO();

                            mercDTO.setId(m.getId());
                            mercDTO.setNome(m.getNome());
                            mercDTO.setDescricao(m.getDescricao());
                            mercDTO.setQuantidade(m.getQuantidade());
                            mercDTO.setValor(m.getValor());
                            mercDTO.setValidade(m.getValidade());
                            mercDTO.setFabricao(m.getFabricao());
                            mercDTO.setCadastro(m.getCadastro());

                            return mercDTO;
                        })
                        .collect(Collectors.toSet())
        );

        // VENDAS
        dto.setVendas(
                entidade.getVendas()
                        .stream()
                        .map(v -> {

                            VendaExibirDTO vendaDTO =
                                    new VendaExibirDTO();

                            vendaDTO.setId(v.getId());
                            vendaDTO.setIdentificacao(v.getIdentificacao());
                            vendaDTO.setCadastro(v.getCadastro());

                            return vendaDTO;
                        })
                        .collect(Collectors.toSet())
        );

        // VECULOS
        dto.setVeiculos(
                entidade.getVeiculos()
                        .stream()
                        .map(v -> {

                            VeiculoExibirDTO veiculoDTO =
                                    new VeiculoExibirDTO();

                            veiculoDTO.setId(v.getId());
                            veiculoDTO.setModelo(v.getModelo());
                            veiculoDTO.setPlaca(v.getPlaca());
                            veiculoDTO.setTipo(v.getTipo());

                            if (v.getProprietario() != null) {
                                UsuarioExibirDTO proprietarioDTO = new UsuarioExibirDTO();
                                proprietarioDTO.setId(v.getProprietario().getId());
                                proprietarioDTO.setNome(v.getProprietario().getNome());
                                proprietarioDTO.setNomeSocial(v.getProprietario().getNomeSocial());
                                proprietarioDTO.setPerfis(v.getProprietario().getPerfis());
                                veiculoDTO.setProprietario(proprietarioDTO);
                            }

                            return veiculoDTO;
                        })
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}