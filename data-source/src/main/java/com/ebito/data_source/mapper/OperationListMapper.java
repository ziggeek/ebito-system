package com.ebito.data_source.mapper;

import com.ebito.data_source.model.dto.OperationDto;
import com.ebito.data_source.model.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {OperationMapper.class})
public interface OperationListMapper {

    OperationListMapper INSTANCE = Mappers.getMapper(OperationListMapper.class);

    List<OperationDto> toListDto(List<Operation> operations);
}
