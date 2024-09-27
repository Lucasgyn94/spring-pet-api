package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.ClienteDTO;
import me.dio.spring_pet_api.domain.dto.PetDTO;
import me.dio.spring_pet_api.domain.model.Cliente;
import me.dio.spring_pet_api.domain.model.Pet;
import me.dio.spring_pet_api.domain.repository.ClienteRepository;
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

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::convertClienteToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
        return convertClienteToDto(cliente);
    }

    @Override
    @Transactional
    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new IllegalArgumentException("ClienteDTO não pode ser nulo");
        }
        Cliente cliente = convertClienteToEntity(clienteDTO);
        try {
            Cliente clienteSalvo = clienteRepository.save(cliente);
            return convertClienteToDto(clienteSalvo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o cliente", e);
        }
    }

    @Override
    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
        if (id == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }
        if (clienteDTO == null) {
            throw new IllegalArgumentException("ClienteDTO não pode ser nulo");
        }
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));

        cliente.setNome(clienteDTO.nome());
        cliente.setTelefone(clienteDTO.telefone());

        try {
            Cliente clienteAtualizado = clienteRepository.save(cliente);
            return convertClienteToDto(clienteAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o cliente", e);
        }
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com o ID: " + id);
        }
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o cliente", e);
        }
    }

    private ClienteDTO convertClienteToDto(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getPets().stream().map(this::convertPetToDto).collect(Collectors.toList())
        );
    }

    private Cliente convertClienteToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.nome());
        cliente.setTelefone(clienteDTO.telefone());
        return cliente;
    }

    private PetDTO convertPetToDto(Pet pet) {
        return new PetDTO(pet.getId(), pet.getNome(), pet.getTipo(), pet.getCliente().getId());
    }
}
