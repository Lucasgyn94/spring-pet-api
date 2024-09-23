package me.dio.spring_pet_api.service.impl;

import jakarta.transaction.Transactional;
import me.dio.spring_pet_api.domain.dto.*;
import me.dio.spring_pet_api.domain.model.*;
import me.dio.spring_pet_api.domain.repository.ServicoRepository;
import me.dio.spring_pet_api.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicoServiceImpl implements ServicoService {
    @Autowired
    private ServicoRepository servicoRepository;


    @Override
    public List<ServicoDTO> listarTodos() {

        return servicoRepository.findAll().stream()
                .map(this::convertServicoToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServicoDTO buscarPorId(Long id) {

        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente de id: " + id + " nao encontrado!"));

        return convertServicoToDto(servico);
    }

    @Override
    public ServicoDTO salvar(ServicoDTO servicoDTO) {
        Servico servico = convertServicoToEntity(servicoDTO);
        return convertServicoToDto(servicoRepository.save(servico));
    }

    @Override
    public ServicoDTO atualizar(Long id, ServicoDTO servicoDtoAtualizado) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Servico de id: " + id + " nao encontrado"));

        servico.setId(servicoDtoAtualizado.id());
        servico.setDescricao(servicoDtoAtualizado.descricao());
        servico.setPreco(servicoDtoAtualizado.preco());

        servico.setPets(servicoDtoAtualizado.pets().stream().map(this::convertPetToEntity).collect(Collectors.toList()));

        return convertServicoToDto(servico);
    }

    @Override
    public void deletar(Long id) {
        servicoRepository.deleteById(id);
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
                        .collect(Collectors.toList())
        );
    }

    private ClienteDTO convertClienteToDto(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone()
        );
    }

    private AgendamentoDTO convertAgendamentoToDto(Agendamento agendamento) {
        return new AgendamentoDTO(
                agendamento.getId(),
                agendamento.getDataHora(),
                convertPetToDto(agendamento.getPet()),
                convertVeterinarioToDto(agendamento.getVeterinario()));
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

    private Servico convertServicoToEntity(ServicoDTO servicoDTO) {
        Servico servico = new Servico();

        servico.setId(servicoDTO.id());
        servico.setDescricao(servicoDTO.descricao());
        servico.setPreco(servicoDTO.preco());

        return servico;
    }

    private Pet convertPetToEntity(PetDTO petDTO) {
        Pet pet = new Pet();

        pet.setId(petDTO.id());
        pet.setNome(petDTO.nome());
        pet.setTipo(petDTO.tipo());
        pet.setServicos(petDTO.servicos().stream()
                .map(this::convertServicoToEntity)
                .collect(Collectors.toList()));
        pet.setAgendamentos(petDTO.agendamentos().stream()
                .map(this::convertAgendamentoToEntinty)
                .collect(Collectors.toList()));

        return pet;
    }

    private Agendamento convertAgendamentoToEntinty(AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = new Agendamento();

        agendamento.setId(agendamentoDTO.id());
        agendamento.setDataHora(agendamentoDTO.dataHora());

        return agendamento;
    }
}
