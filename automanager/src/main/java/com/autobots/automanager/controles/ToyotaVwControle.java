package com.autobots.automanager.controles;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.dto.Documento.DocumentoExibirDTO;
import com.autobots.automanager.dto.Email.EmailExibirDTO;
import com.autobots.automanager.dto.Endereco.EnderecoExibirDTO;
import com.autobots.automanager.dto.Empresa.ServicosMercadoriasExibirDTO;
import com.autobots.automanager.dto.Empresa.VendasPeriodoExibirDTO;
import com.autobots.automanager.dto.Mercadoria.MercadoriaExibirDTO;
import com.autobots.automanager.dto.Servico.ServicoExibirDTO;
import com.autobots.automanager.dto.Telefone.TelefoneExibirDTO;
import com.autobots.automanager.dto.Usuario.UsuarioExibirDTO;
import com.autobots.automanager.dto.Veiculo.VeiculoExibirDTO;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.excecoes.personalizado.EntidadeNaoEncontradaException;
import com.autobots.automanager.repositorios.EmpresaRepositorio;

@RestController
@RequestMapping("/relatorios")
@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
public class ToyotaVwControle {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @GetMapping("/empresas/{id}/clientes")
    public ResponseEntity<List<UsuarioExibirDTO>> listarClientes(@PathVariable Long id) {
        Empresa empresa = buscarEmpresa(id);
        List<UsuarioExibirDTO> clientes = empresa.getUsuarios().stream()
                .filter(u -> u.getPerfis().contains(PerfilUsuario.CLIENTE))
                .map(this::converterUsuario)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/empresas/{id}/funcionarios")
    public ResponseEntity<List<UsuarioExibirDTO>> listarFuncionarios(@PathVariable Long id) {
        Empresa empresa = buscarEmpresa(id);
        List<UsuarioExibirDTO> funcionarios = empresa.getUsuarios().stream()
                .filter(u -> !u.getPerfis().contains(PerfilUsuario.CLIENTE))
                .map(this::converterUsuario)
                .collect(Collectors.toList());
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/empresas/{id}/servicos-mercadorias")
    public ResponseEntity<ServicosMercadoriasExibirDTO> listarServicosMercadorias(@PathVariable Long id) {
        Empresa empresa = buscarEmpresa(id);

        ServicosMercadoriasExibirDTO dto = new ServicosMercadoriasExibirDTO();
        dto.setMercadorias(empresa.getMercadorias().stream().map(this::converterMercadoria).collect(Collectors.toSet()));
        dto.setServicos(empresa.getServicos().stream().map(this::converterServico).collect(Collectors.toSet()));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/empresas/{id}/vendas-periodo")
    public ResponseEntity<VendasPeriodoExibirDTO> listarVendasPeriodo(
            @PathVariable Long id,
            @RequestParam String inicio,
            @RequestParam String fim) {
        Empresa empresa = buscarEmpresa(id);

        Date inicioData;
        Date fimData;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            inicioData = sdf.parse(inicio);
            // Defina fimData para o final do dia
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(fim));
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            fimData = cal.getTime();
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de data invÃ¡lido. Use yyyy-MM-dd");
        }

        Set<MercadoriaExibirDTO> mercadoriasVendidas = new HashSet<>();
        Set<ServicoExibirDTO> servicosVendidos = new HashSet<>();

        empresa.getVendas().stream()
                .filter(v -> v.getCadastro().compareTo(inicioData) >= 0 && v.getCadastro().compareTo(fimData) <= 0)
                .forEach(v -> {
                    v.getMercadorias().forEach(m -> mercadoriasVendidas.add(converterMercadoria(m)));
                    v.getServicos().forEach(s -> servicosVendidos.add(converterServico(s)));
                });

        VendasPeriodoExibirDTO dto = new VendasPeriodoExibirDTO();
        dto.setPecasVendidas(mercadoriasVendidas);
        dto.setServicosVendidos(servicosVendidos);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/empresas/{id}/veiculos")
    public ResponseEntity<List<VeiculoExibirDTO>> listarVeiculos(@PathVariable Long id) {
        Empresa empresa = buscarEmpresa(id);

        // Coleta todos os veÃ­culos associados Ã s vendas da empresa
        Set<Veiculo> veiculosSet = empresa.getVendas().stream()
                .map(Venda::getVeiculo)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<VeiculoExibirDTO> veiculos = veiculosSet.stream()
                .map(this::converterVeiculo)
                .collect(Collectors.toList());

        return ResponseEntity.ok(veiculos);
    }

    private Empresa buscarEmpresa(Long id) {
        return empresaRepositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa nÃ£o encontrada"));
    }

    private UsuarioExibirDTO converterUsuario(Usuario entidade) {
        UsuarioExibirDTO dto = new UsuarioExibirDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setNomeSocial(entidade.getNomeSocial());
        dto.setPerfis(entidade.getPerfis());

        if (entidade.getEndereco() != null) {
            EnderecoExibirDTO enderecoDTO = new EnderecoExibirDTO();
            enderecoDTO.setId(entidade.getEndereco().getId());
            enderecoDTO.setEstado(entidade.getEndereco().getEstado());
            enderecoDTO.setCidade(entidade.getEndereco().getCidade());
            enderecoDTO.setBairro(entidade.getEndereco().getBairro());
            enderecoDTO.setRua(entidade.getEndereco().getRua());
            enderecoDTO.setNumero(entidade.getEndereco().getNumero());
            enderecoDTO.setCep(entidade.getEndereco().getCodigoPostal());
            enderecoDTO.setInformacoesAdicionais(entidade.getEndereco().getInformacoesAdicionais());
            enderecoDTO.setIdCliente(entidade.getId());
            dto.setEndereco(enderecoDTO);
        }

        dto.setTelefones(entidade.getTelefones().stream().map(t -> {
            TelefoneExibirDTO tDTO = new TelefoneExibirDTO();
            tDTO.setId(t.getId());
            tDTO.setDdd(t.getDdd());
            tDTO.setNumero(t.getNumero());
            tDTO.setIdCliente(entidade.getId());
            return tDTO;
        }).collect(Collectors.toSet()));

        dto.setDocumentos(entidade.getDocumentos().stream().map(d -> {
            DocumentoExibirDTO dDTO = new DocumentoExibirDTO();
            dDTO.setId(d.getId());
            dDTO.setTipo(d.getTipo());
            dDTO.setNumero(d.getNumero());
            dDTO.setIdCliente(entidade.getId());
            return dDTO;
        }).collect(Collectors.toSet()));

        dto.setEmails(entidade.getEmails().stream().map(e -> {
            EmailExibirDTO eDTO = new EmailExibirDTO();
            eDTO.setId(e.getId());
            eDTO.setEndereco(e.getEndereco());
            return eDTO;
        }).collect(Collectors.toSet()));

        return dto;
    }

    private MercadoriaExibirDTO converterMercadoria(Mercadoria m) {
        MercadoriaExibirDTO dto = new MercadoriaExibirDTO();
        dto.setId(m.getId());
        dto.setNome(m.getNome());
        dto.setDescricao(m.getDescricao());
        dto.setQuantidade(m.getQuantidade());
        dto.setValor(m.getValor());
        dto.setValidade(m.getValidade());
        dto.setFabricao(m.getFabricao());
        dto.setCadastro(m.getCadastro());
        return dto;
    }

    private ServicoExibirDTO converterServico(Servico s) {
        ServicoExibirDTO dto = new ServicoExibirDTO();
        dto.setId(s.getId());
        dto.setNome(s.getNome());
        dto.setDescricao(s.getDescricao());
        dto.setValor(s.getValor());
        return dto;
    }

    private VeiculoExibirDTO converterVeiculo(Veiculo v) {
        VeiculoExibirDTO dto = new VeiculoExibirDTO();
        dto.setId(v.getId());
        dto.setModelo(v.getModelo());
        dto.setPlaca(v.getPlaca());
        dto.setTipo(v.getTipo());
        if (v.getProprietario() != null) {
            UsuarioExibirDTO propDto = new UsuarioExibirDTO();
            propDto.setId(v.getProprietario().getId());
            propDto.setNome(v.getProprietario().getNome());
            dto.setProprietario(propDto);
        }
        return dto;
    }
}
