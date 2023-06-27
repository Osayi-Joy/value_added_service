package com.digicore.billentbackofficeservice.service.mapper;

import com.digicore.billentbackofficeservice.domain.TransactionLog;
import com.digicore.billentbackofficeservice.service.dto.TransactionLogDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionLogMapper extends EntityMapper<TransactionLogDTO, TransactionLog> {

}
