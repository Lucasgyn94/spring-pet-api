package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.AgendamentoDTO;
import me.dio.spring_pet_api.domain.dto.PetDTO;
import me.dio.spring_pet_api.domain.dto.ServicoDTO;
import me.dio.spring_pet_api.domain.model.Agendamento;
import me.dio.spring_pet_api.domain.model.Pet;
import me.dio.spring_pet_api.domain.model.Servico;
import me.dio.spring_pet_api.domain.repository.PetRepository;
import me.dio.spring_pet_api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;


    @Override
    public List<PetDTO> listarTodos() {
        return petRepository.findAll().stream()
                .map(this::convertPetToDto)
                .collect(Collectors.toList());

    }

    @Override
    public PetDTO buscarPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet nao encontrado com esse id: " + id));

        return convertPetToDto(pet);
    }

    @Override
    public PetDTO salvar(PetDTO petDTO) {
        Pet pet = convertPetToEntity(petDTO);

        return convertPetToDto(petRepository.save(pet));
    }

    @Override
    public PetDTO atualizar(Long id, PetDTO petDtoAtualizado) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet nao encontrado com o id: " + id));

        pet.setNome(petDtoAtualizado.getNome());
        pet.setTipo(petDtoAtualizado.getTipo());
        pet.setServicos(petDtoAtualizado.getServicos().stream()
                .map(this::convertServicoToEntity)
                .collect(Collectors.toList()));
        pet.setAgendamentos(petDtoAtualizado.getAgendamentos().stream()
                .map(this::convertAgendamentoToEntinty)
                .collect(Collectors.toList()));

        return convertPetToDto(petRepository.save(pet));
    }

    @Override
    public void deletar(Long id) {
        petRepository.deleteById(id);
    }

    // conversao Pet em PetDTO
    private PetDTO convertPetToDto(Pet pet) {
        return new PetDTO(
                pet.getId(),
                pet.getNome(),
                pet.getTipo(),
                pet.getServicos().stream()
                        .map(this::convertServicoToDto)
                        .collect(Collectors.toList()),
                pet.getAgendamentos().stream()
                        .map(this::convertAgendamentoToDto)
                        .collect(Collectors.toList())
        );
    }

    // conversao Servico em ServicoDTO
    private ServicoDTO convertServicoToDto(Servico servico) {
        return new ServicoDTO(
                servico.getId(),
                servico.getDescricao(),
                servico.getPreco()
        );
    }

    // conversao Agendamento em AgendamentoDTO
    private AgendamentoDTO convertAgendamentoToDto(Agendamento agendamento) {
        return new AgendamentoDTO(
                agendamento.getId(),
                agendamento.getDataHora()
        );
    }

    // conversao PetDto em Pet
    private Pet convertPetToEntity(PetDTO petDTO) {
        Pet pet = new Pet();

        pet.setId(petDTO.getId());
        pet.setNome(petDTO.getNome());
        pet.setTipo(petDTO.getTipo());
        pet.setServicos(petDTO.getServicos().stream()
                .map(this::convertServicoToEntity)
                .collect(Collectors.toList()));
        pet.setAgendamentos(petDTO.getAgendamentos().stream()
                .map(this::convertAgendamentoToEntinty)
                .collect(Collectors.toList()));

        return pet;
    }

    // conversao ServicoDTO em Servico
    private Servico convertServicoToEntity(ServicoDTO servicoDTO) {
        Servico servico = new Servico();

        servico.setId(servicoDTO.getId());
        servico.setDescricao(servicoDTO.getDescricao());
        servico.setPreco(servicoDTO.getPreco());

        return servico;
    }

    // conversao AgendamentoDTO em Agendamento
    private Agendamento convertAgendamentoToEntinty(AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = new Agendamento();

        agendamento.setId(agendamentoDTO.getId());
        agendamento.setDataHora(agendamentoDTO.getDataHora());

        return agendamento;
    }
}
