package org.nastya.service;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nastya.dto.ShowDictionaryDTO;
import org.nastya.dto.ShowRecordDTO;
import org.nastya.dto.UpdateRecordDTO;
import org.nastya.entity.Dictionary;
import org.nastya.entity.Record;
import org.nastya.repository.DictionaryRepository;
import org.nastya.repository.RecordRepository;
import org.nastya.service.mapper.DictionaryMapperImpl;
import org.nastya.service.mapper.RecordMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DictionaryServiceTest {

    @Mock
    private DictionaryRepository dictionaryRepository;

    @Mock
    private RecordRepository recordRepository;

    private DictionaryService dictionaryService;

    @BeforeEach
    void setUp() {
        DictionaryMapper dictionaryMapper = new DictionaryMapperImpl();
        RecordMapper recordMapper = new RecordMapperImpl(dictionaryMapper, dictionaryRepository);
        dictionaryService = new DictionaryService(dictionaryRepository, recordRepository, recordMapper, dictionaryMapper);
    }

    @Test
    void getAllDictionaries_ShouldReturnListOfDTOs() {
        Dictionary dictionary = new Dictionary();
        dictionary.setId(1);
        dictionary.setName("Test Dictionary");
        List<Dictionary> dictionaries = Collections.singletonList(dictionary);
        ShowDictionaryDTO dto = new ShowDictionaryDTO(1, "Test Dictionary");
        List<ShowDictionaryDTO> expectedDTOs = Collections.singletonList(dto);

        Mockito.when(dictionaryRepository.findAll()).thenReturn(dictionaries);

        List<ShowDictionaryDTO> result = dictionaryService.getAllDictionaries();

        assertEquals(expectedDTOs, result);
        Mockito.verify(dictionaryRepository).findAll();
    }

    @Test
    void saveRecord_NewRecord_ShouldSaveSuccessfully() {
        UpdateRecordDTO dto = new UpdateRecordDTO(null, 1, "Test", "Value");
        Record record = new Record();
        record.setId(null);
        record.setDictionaryId(1);
        record.setName("Test");
        record.setValue("Value");
        Mockito.when(dictionaryRepository.existsById(1)).thenReturn(true);

        dictionaryService.saveRecord(dto);

        Mockito.verify(dictionaryRepository).existsById(1);
        Mockito.verify(recordRepository).save(record);
    }

    @Test
    void saveRecord_ExistingRecord_ShouldSaveSuccessfully() {
        UpdateRecordDTO dto = new UpdateRecordDTO(1, 1, "Test", "Value");
        Record record = new Record();
        record.setId(1);
        record.setDictionaryId(1);
        record.setName("Test");
        record.setValue("Value");

        Mockito.when(dictionaryRepository.existsById(1)).thenReturn(true);
        Mockito.when(recordRepository.existsById(1)).thenReturn(true);

        dictionaryService.saveRecord(dto);

        Mockito.verify(recordRepository).save(record);
    }

    @Test
    void saveRecord_NonExistingDictionary_ShouldThrowException() {
        UpdateRecordDTO dto = new UpdateRecordDTO(null, 1, "Test", "Value");

        Mockito.when(dictionaryRepository.existsById(1)).thenReturn(false);

        assertThrows(ObjectNotFoundException.class, () -> dictionaryService.saveRecord(dto));
        Mockito.verify(recordRepository, times(0)).save(any());
    }

    @Test
    void saveRecord_NonExistingRecord_ShouldThrowException() {
        UpdateRecordDTO dto = new UpdateRecordDTO(1, 1, "Test", "Value");

        Mockito.when(dictionaryRepository.existsById(1)).thenReturn(true);
        Mockito.when(recordRepository.existsById(1)).thenReturn(false);

        assertThrows(ObjectNotFoundException.class, () -> dictionaryService.saveRecord(dto));
        Mockito.verify(recordRepository, times(0)).save(any());
    }

    @Test
    void deleteRecord_ShouldCallRepository() {
        dictionaryService.deleteRecord(1);

        Mockito.verify(recordRepository).deleteById(1);
    }

    @Test
    void deleteRecord_NonExistingRecord_ShouldNotThrowException() {
        Integer nonExistingId = -1;

        Mockito.doNothing().when(recordRepository).deleteById(nonExistingId);

        assertDoesNotThrow(() -> dictionaryService.deleteRecord(nonExistingId));

        Mockito.verify(recordRepository).deleteById(nonExistingId);
    }

    @Test
    void searchRecordByName_ExistingRecord_ShouldReturnDTO() {
        String keyword = "test";
        Integer dictionaryId = 1;

        Record record = new Record();
        record.setId(1);
        record.setDictionaryId(1);
        record.setName("test");
        record.setValue("value");

        Dictionary dictionary = new Dictionary();
        dictionary.setId(dictionaryId);
        dictionary.setName("Test Dictionary");

        ShowDictionaryDTO dictionaryDTO = new ShowDictionaryDTO(dictionaryId, "Test Dictionary");
        ShowRecordDTO expectedDTO = new ShowRecordDTO(1, dictionaryDTO, "test", "value");

        Mockito.when(recordRepository.findByNameAndDictionaryId(keyword, dictionaryId))
                .thenReturn(Optional.of(record));

        Mockito.when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.of(dictionary));

        ShowRecordDTO result = dictionaryService.searchRecordByName(keyword, dictionaryId);

        assertEquals(expectedDTO, result);
        Mockito.verify(recordRepository).findByNameAndDictionaryId(keyword, dictionaryId);
    }

    @Test
    void searchRecordByName_NonExistingRecord_ShouldThrowException() {
        String keyword = "test";
        Integer dictionaryId = 1;

        Mockito.when(recordRepository.findByNameAndDictionaryId(keyword, dictionaryId))
                .thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () ->
                dictionaryService.searchRecordByName(keyword, dictionaryId));
        Mockito.verify(recordRepository).findByNameAndDictionaryId(keyword, dictionaryId);
    }
}
