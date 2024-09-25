package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.ServicoDTO;
import me.dio.spring_pet_api.domain.model.Pet;
import me.dio.spring_pet_api.domain.model.Servico;
import me.dio.spring_pet_api.domain.repository.PetRepository;
import me.dio.spring_pet_api.domain.repository.ServicoRepository;
import me.dio.spring_pet_api.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ServicoDTO> listarTodos() {
        return servicoRepository.findAll().stream()
                .map(this::convertServicoToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ServicoDTO buscarPorId(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com o ID: " + id));
        return convertServicoToDto(servico);
    }

    @Override
    @Transactional
    public ServicoDTO salvar(ServicoDTO servicoDTO) {
        if (servicoDTO == null) {
            throw new IllegalArgumentException("ServicoDTO não pode ser nulo");
        }

        Servico servico = new Servico();
        servico.setDescricao(servicoDTO.descricao());
        servico.setPreco(servicoDTO.preco());

        try {
            Servico servicoSalvo = servicoRepository.save(servico);
            return convertServicoToDto(servicoSalvo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o serviço", e);
        }
    }

    @Override
    @Transactional
    public ServicoDTO atualizar(Long id, ServicoDTO servicoDTO) {
        if (id == null) {
            throw new IllegalArgumentException("ID do serviço não pode ser nulo");
        }

        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com o ID: " + id));

        servico.setDescricao(servicoDTO.descricao());
        servico.setPreco(servicoDTO.preco());


        try {
            Servico servicoAtualizado = servicoRepository.save(servico);
            return convertServicoToDto(servicoAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o serviço", e);
        }
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do serviço não pode ser nulo");
        }

        if (!servicoRepository.existsById(id)) {
            throw new RuntimeException("Serviço não encontrado com o ID: " + id);
        }

        try {
            servicoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o serviço", e);
        }
    }

    private ServicoDTO convertServicoToDto(Servico servico) {
        return new ServicoDTO(
                servico.getId(),
                servico.getDescricao(),
                servico.getPreco()
        );
    }

    private Servico convertServicoToEntity(ServicoDTO dto) {
        Servico servico = new Servico();
        servico.setId(dto.id());
        servico.setDescricao(dto.descricao());
        servico.setPreco(dto.preco());
        return servico;
    }
}
