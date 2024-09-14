package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.AgendamentoDTO;
import me.dio.spring_pet_api.domain.repository.AgendamentoRepository;
import me.dio.spring_pet_api.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;


    @Override
    public List<AgendamentoDTO> listarTodos() {
        return List.of();
    }

    @Override
    public AgendamentoDTO buscarPorId(Long id) {
        return null;
    }

    @Override
    public AgendamentoDTO salvar(AgendamentoDTO agendamentoDTO) {
        return null;
    }

    @Override
    public AgendamentoDTO atualizar(Long id, AgendamentoDTO agendamentoDtoAtualizado) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }
}
