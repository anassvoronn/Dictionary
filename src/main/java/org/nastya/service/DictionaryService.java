package org.nastya.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.nastya.dto.ShowDictionaryDTO;
import org.nastya.dto.ShowRecordDTO;
import org.nastya.dto.UpdateRecordDTO;
import org.nastya.entity.Dictionary;
import org.nastya.entity.Record;
import org.nastya.repository.DictionaryRepository;
import org.nastya.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final DictionaryMapper dictionaryMapper;

    public List<ShowDictionaryDTO> getAllDictionaries() {
        log.info("Fetching all dictionaries");
        List<Dictionary> dictionaries = dictionaryRepository.findAll();
        return dictionaryMapper.mapToDTO(dictionaries);
    }

    public void saveRecord(UpdateRecordDTO dto) {
        Integer dictionaryId = dto.getDictionary().getId();
        Integer recordId = dto.getId();

        log.info("Validation of {} ", dto);

        validateDictionary(dictionaryId);
        validateRecord(recordId);

        log.info("Saving of {}", dto);

        Record record = recordMapper.mapToEntity(dto);
        recordRepository.save(record);
        log.info("Record saved successfully: {}", record);
    }

    public void deleteRecord(Integer id) {
        log.info("Deleting record with id={}", id);
        recordRepository.deleteById(id);
    }

    public ShowRecordDTO searchRecordByName(String keyword, Integer dictionaryId) {
        log.info("Searching records with name '{}'", keyword);
        return recordRepository.findByNameAndDictionaryId(keyword, dictionaryId)
                .map(recordMapper::mapToDTO)
                .orElseThrow(() -> new ObjectNotFoundException((Object) keyword, Record.class.getName()));
    }

    private void validateDictionary(Integer dictionaryId) {
        if (!dictionaryRepository.existsById(dictionaryId)) {
            throw new ObjectNotFoundException(dictionaryId, Dictionary.class.getName());
        }
    }

    private void validateRecord(Integer recordId) {
        if (recordId == null) {
            return;
        }
        if (!recordRepository.existsById(recordId)) {
            throw new ObjectNotFoundException(recordId, Record.class.getName());
        }
    }
}
