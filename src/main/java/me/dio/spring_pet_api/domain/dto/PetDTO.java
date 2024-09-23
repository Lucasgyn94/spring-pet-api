package me.dio.spring_pet_api.domain.dto;

import java.util.List;



public record PetDTO (
        Long id,
        String nome,
        String tipo,
        ClienteDTO clienteDTO,
        List<ServicoDTO> servicos,
        List<AgendamentoDTO> agendamentos
){ }
