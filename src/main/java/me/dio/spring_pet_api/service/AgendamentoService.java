package me.dio.spring_pet_api.service;

import me.dio.spring_pet_api.domain.dto.AgendamentoDTO;
import me.dio.spring_pet_api.domain.model.Agendamento;

import java.util.List;

public interface AgendamentoService {
    List<AgendamentoDTO> listarTodos();

    AgendamentoDTO buscarPorId(Long id);

    AgendamentoDTO salvar(AgendamentoDTO agendamentoDTO);

    AgendamentoDTO atualizar(Long id, AgendamentoDTO agendamentoDtoAtualizado);

    void deletar(Long id);

}
