package com.ebito.data_source.mapper;

import com.ebito.data_source.model.dto.OperationDto;
import com.ebito.data_source.model.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationMapper {

    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

    OperationDto toDto(Operation operation);
}
