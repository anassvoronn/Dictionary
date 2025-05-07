package org.nastya.controller;

import lombok.RequiredArgsConstructor;
import org.nastya.dto.ShowDictionaryDTO;
import org.nastya.dto.ShowRecordDTO;
import org.nastya.dto.UpdateRecordDTO;
import org.nastya.service.DictionaryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @GetMapping
    public List<ShowDictionaryDTO> getAllDictionaries() {
        return dictionaryService.getAllDictionaries();
    }

    @PostMapping("/record")
    public void saveRecord(@RequestBody UpdateRecordDTO dto) {
        dictionaryService.saveRecord(dto);
    }

    @DeleteMapping("/record/{recordId}")
    public void deleteRecord(@PathVariable Integer recordId) {
        dictionaryService.deleteRecord(recordId);
    }

    @GetMapping("{dictionaryId}/record")
    public ShowRecordDTO searchRecordByName(@RequestParam String keyword, @PathVariable Integer dictionaryId) {
        return dictionaryService.searchRecordByName(keyword, dictionaryId);
    }
}
