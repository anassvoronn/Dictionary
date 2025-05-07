package org.nastya.service.mapper;

import lombok.RequiredArgsConstructor;
import org.nastya.dto.ShowRecordDTO;
import org.nastya.dto.UpdateRecordDTO;
import org.nastya.entity.Dictionary;
import org.nastya.entity.Record;
import org.nastya.repository.DictionaryRepository;
import org.nastya.service.DictionaryMapper;
import org.nastya.service.RecordMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class RecordMapperImpl implements RecordMapper {

    private final DictionaryMapper dictionaryMapper;
    private final DictionaryRepository dictionaryRepository;

    @Override
    public ShowRecordDTO mapToDTO(Record record) {
        Assert.notNull(record, "Entity must not be null");
        Dictionary dictionary = dictionaryRepository.findById(record.getDictionaryId())
                .orElseThrow(() -> new IllegalStateException("Dictionary with " + record.getDictionaryId() + " id was not found"));
        return new ShowRecordDTO(
                record.getId(),
                dictionaryMapper.mapToDTO(dictionary),
                record.getName(),
                record.getValue()
        );
    }

    @Override
    public Record mapToEntity(UpdateRecordDTO dto) {
        Record record = new Record();
        record.setId(dto.getId());
        record.setDictionaryId(dto.getDictionary().getId());
        record.setName(dto.getName());
        record.setValue(dto.getValue());
        return record;
    }
}
