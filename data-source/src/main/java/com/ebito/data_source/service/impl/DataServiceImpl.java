package com.ebito.data_source.service.impl;

import com.ebito.data_source.api.controller.response.DataResponse;
import com.ebito.data_source.mapper.ClientMapper;
import com.ebito.data_source.model.entity.Client;
import com.ebito.data_source.repository.ClientRepository;
import com.ebito.data_source.service.DataService;
import com.ebito.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final ClientRepository clientRepository;

    @Override
    public DataResponse getData(final String clientId) {
        Assert.hasText(clientId, "Client ID cannot be empty");
        Client client = clientRepository.findById(Long.parseLong(clientId))
                .orElseThrow(() -> new ResourceNotFoundException("Клиент не найден"));
        return ClientMapper.INSTANCE.toDto(client);
    }
}
