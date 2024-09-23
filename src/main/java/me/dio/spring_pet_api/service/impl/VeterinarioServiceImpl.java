package me.dio.spring_pet_api.service.impl;

import jakarta.transaction.Transactional;
import me.dio.spring_pet_api.domain.dto.*;
import me.dio.spring_pet_api.domain.model.*;
import me.dio.spring_pet_api.domain.repository.VeterinarioRepository;
import me.dio.spring_pet_api.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;


    @Override
    public List<VeterinarioDTO> listarTodos() {
        return veterinarioRepository.findAll().stream()
                .map(this::convertVeterinarioToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VeterinarioDTO buscarPorId(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinario de id: " + id + " nao encontrado"));

        return convertVeterinarioToDto(veterinario);
    }

    @Override
    public VeterinarioDTO salvar(VeterinarioDTO veterinarioDTO) {
        Veterinario veterinario = convertVeterinarioToEntity(veterinarioDTO);

        return convertVeterinarioToDto(veterinarioRepository.save(veterinario));
    }

    @Override
    public VeterinarioDTO atualizar(Long id, VeterinarioDTO veterinarioDtoAtualizado) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinario de id: " + id + " nao encontrado!"));

        veterinario.setId(veterinarioDtoAtualizado.id());
        veterinario.setNome(veterinarioDtoAtualizado.nome());
        veterinario.setEspecialidade(veterinarioDtoAtualizado.especialidade());
        veterinario.setAgendamentos(veterinarioDtoAtualizado.agendamentos().stream()
                .map(this::convertAgendamentoToEntity)
                .collect(Collectors.toList()));
        return convertVeterinarioToDto(veterinarioRepository.save(veterinario));

    }

    @Override
    public void deletar(Long id) {
        veterinarioRepository.deleteById(id);
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
                cliente.getTelefone()
        );
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

    private Cliente convertClienteToEntity(ClienteDTO clienteDTO) {
        return new Cliente(
                null,
                clienteDTO.nome(),
                clienteDTO.telefone()
        );
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


}
