package com.ebito.data_source.mapper;

import com.ebito.data_source.api.controller.response.DataResponse;
import com.ebito.data_source.model.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {OperationMapper.class, OperationListMapper.class, ClientMapper.class})
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "accountNumber", source = "client.account.accountNumber")
    @Mapping(target = "accountCurrency", source = "client.account.accountCurrency")
    @Mapping(target = "agreementNumber", source = "client.agreement.agreementNumber")
    @Mapping(target = "agreementDate", source = "client.agreement.agreementDate")
    @Mapping(target = "accountOperations", source = "client.account.operations")
    DataResponse toDto(Client client);

}
