package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.*;
import me.dio.spring_pet_api.domain.model.*;
import me.dio.spring_pet_api.domain.repository.ClienteRepository;
import me.dio.spring_pet_api.domain.repository.PetRepository;
import me.dio.spring_pet_api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetServiceImpl implements PetService {


    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PetDTO> listarTodos() {
        return petRepository.findAll().stream()
                .map(this::convertPetToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PetDTO buscarPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet nao encontrado com esse id: " + id));

        // Forçar o carregamento do cliente
        if (pet.getCliente() != null) {
            pet.getCliente().getNome();
        }

        return convertPetToDto(pet);
    }

    @Override
    @Transactional
    public PetDTO salvar(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setNome(petDTO.nome());
        pet.setTipo(petDTO.tipo());

        if (petDTO.clienteDTO() != null && petDTO.clienteDTO().id() != null) {
            Cliente cliente = clienteRepository.findById(petDTO.clienteDTO().id())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + petDTO.clienteDTO().id()));
            pet.setCliente(cliente);
        } else {
            throw new RuntimeException("ClienteDTO ou ID do cliente não pode ser null");
        }

        Pet petSalvo = petRepository.save(pet);
        return convertPetToDto(petSalvo);
    }





    @Override
    @Transactional
    public PetDTO atualizar(Long id, PetDTO petDtoAtualizado) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet nao encontrado com o id: " + id));

        pet.setNome(petDtoAtualizado.nome());
        pet.setTipo(petDtoAtualizado.tipo());

        if (petDtoAtualizado.clienteDTO() != null) {
            Cliente cliente = clienteRepository.findById(petDtoAtualizado.clienteDTO().id())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            pet.setCliente(cliente);
        }

        // Atualizar serviços e agendamentos se necessário

        return convertPetToDto(petRepository.save(pet));
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        petRepository.deleteById(id);
    }

    private PetDTO convertPetToDto(Pet pet) {
        ClienteDTO clienteDTO = null;
        if (pet.getCliente() != null) {
            clienteDTO = new ClienteDTO(
                    pet.getCliente().getId(),
                    pet.getCliente().getNome(),
                    pet.getCliente().getTelefone()
            );
        }

        return new PetDTO(
                pet.getId(),
                pet.getNome(),
                pet.getTipo(),
                clienteDTO,
                pet.getServicos() != null ? pet.getServicos().stream()
                        .map(this::convertServicoToDto)
                        .collect(Collectors.toList()) : null,
                pet.getAgendamentos() != null ? pet.getAgendamentos().stream()
                        .map(this::convertAgendamentoToDto)
                        .collect(Collectors.toList()) : null
        );
    }



    private Pet convertPetToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.id());
        pet.setNome(petDTO.nome());
        pet.setTipo(petDTO.tipo());

        if (petDTO.clienteDTO() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(petDTO.clienteDTO().id());
            cliente.setNome(petDTO.clienteDTO().nome());
            cliente.setTelefone(petDTO.clienteDTO().telefone());
            pet.setCliente(cliente);
        }

        if (petDTO.servicos() != null) {
            pet.setServicos(petDTO.servicos().stream()
                    .map(this::convertServicoToEntity)
                    .collect(Collectors.toList()));
        }
        if (petDTO.agendamentos() != null) {
            pet.setAgendamentos(petDTO.agendamentos().stream()
                    .map(this::convertAgendamentoToEntity)
                    .collect(Collectors.toList()));
        }
        return pet;
    }


    // conversao Servico em ServicoDTO
    private ServicoDTO convertServicoToDto(Servico servico) {
        return new ServicoDTO(
                servico.getId(),
                servico.getDescricao(),
                servico.getPreco(),
                servico.getPets().stream()
                        .map(this::convertPetToDto)
                        .collect(Collectors.toList()));
    }

    // conversao ServicoDTO em Servico
    private Servico convertServicoToEntity(ServicoDTO servicoDTO) {
        Servico servico = new Servico();

        servico.setId(servicoDTO.id());
        servico.setDescricao(servicoDTO.descricao());
        servico.setPreco(servicoDTO.preco());

        return servico;
    }

    // conversao Agendamento em AgendamentoDTO
    private AgendamentoDTO convertAgendamentoToDto(Agendamento agendamento) {
        return new AgendamentoDTO(
                agendamento.getId(),
                agendamento.getDataHora(),
                convertPetToDto(agendamento.getPet()),
                convertVeterinarioToDto(agendamento.getVeterinario()));
    }


    // conversao AgendamentoDTO em Agendamento
    private Agendamento convertAgendamentoToEntity(AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = new Agendamento();

        agendamento.setId(agendamentoDTO.id());
        agendamento.setDataHora(agendamentoDTO.dataHora());

        return agendamento;
    }

    private ClienteDTO convertClienteToDto(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone()
        );
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
}
