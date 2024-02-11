package com.ebito.data_aggregator.mapper;

import com.ebito.data_aggregator.client.outer.response.OperationDto;
import com.ebito.data_aggregator.api.controller.request.reference.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "dateTime", source = "dto.timestamp")
    TransactionDTO toDto(OperationDto dto);

    List<TransactionDTO> toListDto(List<OperationDto> dtos);
}
