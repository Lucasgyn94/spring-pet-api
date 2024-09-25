package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.AgendamentoDTO;
import me.dio.spring_pet_api.domain.model.Agendamento;
import me.dio.spring_pet_api.domain.model.Pet;
import me.dio.spring_pet_api.domain.model.Servico;
import me.dio.spring_pet_api.domain.model.Veterinario;
import me.dio.spring_pet_api.domain.repository.AgendamentoRepository;
import me.dio.spring_pet_api.domain.repository.PetRepository;
import me.dio.spring_pet_api.domain.repository.ServicoRepository;
import me.dio.spring_pet_api.domain.repository.VeterinarioRepository;
import me.dio.spring_pet_api.exception.ResourceNotFoundException;
import me.dio.spring_pet_api.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PetRepository petRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final ServicoRepository servicoRepository;

    @Autowired
    public AgendamentoServiceImpl(AgendamentoRepository agendamentoRepository,
                                  PetRepository petRepository,
                                  VeterinarioRepository veterinarioRepository,
                                  ServicoRepository servicoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.petRepository = petRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.servicoRepository = servicoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AgendamentoDTO buscarPorId(Long id) {
        return agendamentoRepository.findByIdWithDetails(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID: " + id));
    }

    @Override
    public AgendamentoDTO salvar(AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = new Agendamento();
        updateAgendamentoFromDto(agendamento, agendamentoDTO);
        return convertToDto(agendamentoRepository.save(agendamento));
    }

    @Override
    public AgendamentoDTO atualizar(Long id, AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID: " + id));
        updateAgendamentoFromDto(agendamento, agendamentoDTO);
        return convertToDto(agendamentoRepository.save(agendamento));
    }

    @Override
    public void deletar(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agendamento não encontrado com ID: " + id);
        }
        agendamentoRepository.deleteById(id);
    }

    private void updateAgendamentoFromDto(Agendamento agendamento, AgendamentoDTO dto) {
        agendamento.setDataHora(dto.dataHora());
        agendamento.setStatus(dto.status());

        Pet pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com ID: " + dto.petId()));
        agendamento.setPet(pet);

        Veterinario veterinario = veterinarioRepository.findById(dto.veterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com ID: " + dto.veterinarioId()));
        agendamento.setVeterinario(veterinario);

        List<Servico> servicos = dto.servicoIds().stream()
                .map(servicoId -> servicoRepository.findById(servicoId)
                        .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado com ID: " + servicoId)))
                .collect(Collectors.toList());
        agendamento.setServicos(servicos);
    }

    private AgendamentoDTO convertToDto(Agendamento agendamento) {
        return new AgendamentoDTO(
                agendamento.getId(),
                agendamento.getDataHora(),
                agendamento.getPet().getId(),
                agendamento.getVeterinario().getId(),
                agendamento.getServicos().stream().map(Servico::getId).collect(Collectors.toList()),
                agendamento.getStatus()
        );
    }
}