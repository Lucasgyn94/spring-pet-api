package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.model.Pet;
import me.dio.spring_pet_api.domain.repository.PetRepository;
import me.dio.spring_pet_api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public List<Pet> listarTodos() {
        return petRepository.findAll();
    }

    @Override
    public Pet buscarPorId(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet com esse id nao encontrado!"));
    }

    @Override
    public Pet salvar(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet atualizar(Long id, Pet petAtualizado) {
        Pet pet = buscarPorId(id);
        pet.setNome(petAtualizado.getNome());
        pet.setTipo(petAtualizado.getTipo());
        pet.setCliente(petAtualizado.getCliente());
        pet.setServicos(petAtualizado.getServicos());
        pet.setAgendamentos(petAtualizado.getAgendamentos());

        return pet;
    }

    @Override
    public void deletar(Long id) {
        petRepository.deleteById(id);
    }
}
