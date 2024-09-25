package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.ClienteDTO;
import me.dio.spring_pet_api.domain.dto.PetDTO;
import me.dio.spring_pet_api.domain.model.Cliente;
import me.dio.spring_pet_api.domain.model.Pet;
import me.dio.spring_pet_api.domain.repository.ClienteRepository;
import me.dio.spring_pet_api.domain.repository.PetRepository;
import me.dio.spring_pet_api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PetDTO> listarTodos() {
        return petRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PetDTO buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do pet não pode ser nulo");
        }
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com o ID: " + id));
        return convertToDto(pet);
    }

    @Override
    @Transactional
    public PetDTO salvar(PetDTO petDTO) {
        if (petDTO == null) {
            throw new IllegalArgumentException("PetDTO não pode ser nulo");
        }
        Pet pet = convertToEntity(petDTO);
        if (petDTO.clienteId() != null) {
            Cliente cliente = clienteRepository.findById(petDTO.clienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + petDTO.clienteId()));
            pet.setCliente(cliente);
        } else {
            throw new IllegalArgumentException("Cliente do pet não pode ser nulo");
        }
        try {
            Pet petSalvo = petRepository.save(pet);
            return convertToDto(petSalvo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o pet", e);
        }
    }

    @Override
    @Transactional
    public PetDTO atualizar(Long id, PetDTO petDTO) {
        if (id == null) {
            throw new IllegalArgumentException("ID do pet não pode ser nulo");
        }
        if (petDTO == null) {
            throw new IllegalArgumentException("PetDTO não pode ser nulo");
        }
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com o ID: " + id));

        pet.setNome(petDTO.nome());
        pet.setTipo(petDTO.tipo());

        if (petDTO.clienteId() != null) {
            Cliente cliente = clienteRepository.findById(petDTO.clienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + petDTO.clienteId()));
            pet.setCliente(cliente);
        }

        try {
            Pet petAtualizado = petRepository.save(pet);
            return convertToDto(petAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o pet", e);
        }
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do pet não pode ser nulo");
        }
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Pet não encontrado com o ID: " + id);
        }
        try {
            petRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o pet", e);
        }
    }

    private PetDTO convertToDto(Pet pet) {
        return new PetDTO(
                pet.getId(),
                pet.getNome(),
                pet.getTipo(),
                pet.getCliente().getId()
        );
    }

    private Pet convertToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setNome(petDTO.nome());
        pet.setTipo(petDTO.tipo());
        return pet;
    }
}
