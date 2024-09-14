package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.ServicoDTO;
import me.dio.spring_pet_api.domain.repository.ServicoRepository;
import me.dio.spring_pet_api.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoServiceImpl implements ServicoService {
    @Autowired
    private ServicoRepository servicoRepository;


    @Override
    public List<ServicoDTO> listarTodos() {
        return List.of();
    }

    @Override
    public ServicoDTO buscarPorId(Long id) {
        return null;
    }

    @Override
    public ServicoDTO salvar(ServicoDTO servicoDTO) {
        return null;
    }

    @Override
    public ServicoDTO atualizar(Long id, ServicoDTO servicoDtoAtualizado) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }
}
