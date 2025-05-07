package org.nastya.service;

import org.nastya.dto.ShowRecordDTO;
import org.nastya.dto.UpdateRecordDTO;
import org.nastya.entity.Record;

public interface RecordMapper {

    ShowRecordDTO mapToDTO(Record record);

    Record mapToEntity(UpdateRecordDTO dto);
}
