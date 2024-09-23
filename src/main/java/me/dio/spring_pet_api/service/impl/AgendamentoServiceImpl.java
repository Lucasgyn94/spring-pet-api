package me.dio.spring_pet_api.service.impl;

import jakarta.transaction.Transactional;
import me.dio.spring_pet_api.domain.dto.*;
import me.dio.spring_pet_api.domain.model.*;
import me.dio.spring_pet_api.domain.repository.AgendamentoRepository;
import me.dio.spring_pet_api.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;


    @Override
    public List<AgendamentoDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(this::convertAgendamentoToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AgendamentoDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento com id: " + id + " nao encontrado!"));
        return convertAgendamentoToDto(agendamento);
    }

    @Override
    public AgendamentoDTO salvar(AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = convertAgendamentoToEntity(agendamentoDTO);
        return null;
    }

    @Override
    public AgendamentoDTO atualizar(Long id, AgendamentoDTO agendamentoDtoAtualizado) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }

    private AgendamentoDTO convertAgendamentoToDto(Agendamento agendamento) {
        return new AgendamentoDTO(
                agendamento.getId(),
                agendamento.getDataHora(),

                convertPetToDto(agendamento.getPet()),
                convertVeterinarioToDto(agendamento.getVeterinario()));
    }

    private Agendamento convertAgendamentoToEntity(AgendamentoDTO agendamentoDTO) {
        return new Agendamento(
                agendamentoDTO.id(),
                agendamentoDTO.dataHora(),
                convertPetToEntity(agendamentoDTO.petDTO()),
                convertVeterinarioToEntity(agendamentoDTO.veterinarioDTO()));
    }

    private VeterinarioDTO convertVeterinarioToDto(Veterinario veterinario) {
        return new VeterinarioDTO(
                veterinario.getId(),
                veterinario.getNome(),
                veterinario.getEspecialidade(),
                veterinario.getAgendamentos().stream()
                        .map(this::convertAgendamentoToDto)
                        .collect(Collectors.toList()));
    }

    private Veterinario convertVeterinarioToEntity(VeterinarioDTO veterinarioDTO) {
        return new Veterinario(
                veterinarioDTO.id(),
                veterinarioDTO.nome(),
                veterinarioDTO.especialidade(),
                veterinarioDTO.agendamentos().stream()
                        .map(this::convertAgendamentoToEntity)
                        .collect(Collectors.toList()));
    }

    private Pet convertPetToEntity(PetDTO petDTO) {
        return new Pet(
                petDTO.id(),
                petDTO.nome(),
                petDTO.tipo(),
                convertClienteToEntity(petDTO.clienteDTO()),
                petDTO.servicos().stream()
                        .map(this::convertServicoToEntity)
                        .collect(Collectors.toList()),
                petDTO.agendamentos().stream()
                        .map(this::convertAgendamentoToEntity)
                        .collect(Collectors.toList()));
    }

    private ClienteDTO convertClienteToDto(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone());
    }

    private Cliente convertClienteToEntity(ClienteDTO clienteDTO) {
        return new Cliente(
                null,
                clienteDTO.nome(),
                clienteDTO.telefone());
    }

    private ServicoDTO convertServicoToDto(Servico servico) {
        return new ServicoDTO(
                servico.getId(),
                servico.getDescricao(),
                servico.getPreco(),
                servico.getPets().stream()
                        .map(this::convertPetToDto)
                        .collect(Collectors.toList()));
    }

    private Servico convertServicoToEntity(ServicoDTO servicoDTO) {
        return new Servico(
                servicoDTO.id(),
                servicoDTO.descricao(),
                servicoDTO.preco(),
                servicoDTO.pets().stream()
                        .map(this::convertPetToEntity)
                        .collect(Collectors.toList()));
    }

    private PetDTO convertPetToDto(Pet pet) {
        return new PetDTO(
                pet.getId(),
                pet.getNome(),
                pet.getTipo(),
                convertClienteToDto(pet.getCliente()),

                pet.getServicos().stream()
                        .map(this::convertServicoToDto)
                        .collect(Collectors.toList()),
                pet.getAgendamentos().stream()
                        .map(this::convertAgendamentoToDto)
                        .collect(Collectors.toList()));
    }

}
