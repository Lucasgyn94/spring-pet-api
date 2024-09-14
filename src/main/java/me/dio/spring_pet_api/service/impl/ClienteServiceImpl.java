package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.AgendamentoDTO;
import me.dio.spring_pet_api.domain.dto.ClienteDTO;
import me.dio.spring_pet_api.domain.dto.PetDTO;
import me.dio.spring_pet_api.domain.dto.ServicoDTO;
import me.dio.spring_pet_api.domain.model.Cliente;
import me.dio.spring_pet_api.domain.model.Pet;
import me.dio.spring_pet_api.domain.repository.ClienteRepository;
import me.dio.spring_pet_api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::convertClienteToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO buscarPorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
        return convertClienteToDto(cliente);
    }

    @Override
    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        Cliente cliente = convertClienteToEntity(clienteDTO);
        return convertClienteToDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDTO atualizar(Long id, ClienteDTO clienteAtualizadoDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com esse id: " + id));

        cliente.setNome(clienteAtualizadoDTO.getNome());
        cliente.setTelefone(clienteAtualizadoDTO.getTelefone());
        cliente.setPets(clienteAtualizadoDTO.getPets().stream()
                .map(this::convertPetToEntity)
                .collect(Collectors.toList()));

        return convertClienteToDto(clienteRepository.save(cliente));
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    // conversao Cliente me ClienteDTO
    private ClienteDTO convertClienteToDto(Cliente cliente) {
        ClienteDTO clienteConvertido = new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getPets() != null ? cliente.getPets().stream()
                        .map(this::convertPetToDto)
                        .collect(Collectors.toList()) : null
        );

        return null;
    }

    private PetDTO convertPetToDto(Pet pet) {
        return new PetDTO(
                pet.getId(),
                pet.getNome(),
                pet.getTipo(),
                pet.getServicos() != null ? pet.getServicos().stream()
                        .map(servico -> new ServicoDTO(servico.getId(), servico.getDescricao(), servico.getPreco()))
                        .collect(Collectors.toList()) : null,
                pet.getAgendamentos() != null ? pet.getAgendamentos().stream()
                        .map(agendamento -> new AgendamentoDTO(agendamento.getId(), agendamento.getDataHora()))
                        .collect(Collectors.toList()) : null
        );
    }

    private Cliente convertClienteToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();

        cliente.setId(clienteDTO.getId());
        cliente.setNome(clienteDTO.getNome());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setPets(clienteDTO.getPets() != null ? clienteDTO.getPets().stream()
                .map(this::convertPetToEntity)
                .collect(Collectors.toList()) : null);

        return cliente;
    }

    private Pet convertPetToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setNome(petDTO.getNome());
        pet.setTipo(petDTO.getTipo());
        return pet;
    }

}
