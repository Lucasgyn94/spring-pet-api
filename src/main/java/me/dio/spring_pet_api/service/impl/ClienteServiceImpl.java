package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.*;
import me.dio.spring_pet_api.domain.model.*;
import me.dio.spring_pet_api.domain.repository.ClienteRepository;
import me.dio.spring_pet_api.domain.repository.ServicoRepository;
import me.dio.spring_pet_api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    @Transactional
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::convertClienteToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteDTO buscarPorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
        return convertClienteToDto(cliente);
    }

    @Override
    @Transactional
    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        Cliente cliente = convertClienteToEntity(clienteDTO);
        return convertClienteToDto(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO clienteAtualizadoDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com esse id: " + id));

        cliente.setNome(clienteAtualizadoDTO.nome());
        cliente.setTelefone(clienteAtualizadoDTO.telefone());

        return convertClienteToDto(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    // conversao Cliente me ClienteDTO
    private ClienteDTO convertClienteToDto(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone()
        );
    }

    private Cliente convertClienteToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.nome());
        cliente.setTelefone(clienteDTO.telefone());

        return cliente;
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
        Pet pet = new Pet();
        pet.setId(petDTO.id());
        pet.setNome(petDTO.nome());
        pet.setTipo(petDTO.tipo());
        return pet;
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

}
