package org.nastya.controller;

import lombok.RequiredArgsConstructor;
import org.nastya.dto.ShowDictionaryDTO;
import org.nastya.dto.ShowRecordDTO;
import org.nastya.dto.UpdateRecordDTO;
import org.nastya.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionary")
@Tag(name = "Dictionary Controller", description = "Управление словарями и их записями")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Operation(summary = "Получить все словари")
    @ApiResponse(responseCode = "200", description = "Успешное получение списка словарей")
    @GetMapping
    public List<ShowDictionaryDTO> getAllDictionaries() {
        return dictionaryService.getAllDictionaries();
    }

    @Operation(summary = "Сохранить запись в словарь")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись успешно сохранена"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping("/record")
    public void saveRecord(
            @RequestBody UpdateRecordDTO dto
    ) {
        dictionaryService.saveRecord(dto);
    }

    @Operation(summary = "Удалить запись по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @DeleteMapping("/record/{recordId}")
    public void deleteRecord(
            @Parameter(description = "ID записи для удаления") @PathVariable Integer recordId
    ) {
        dictionaryService.deleteRecord(recordId);
    }

    @Operation(summary = "Поиск записи по ключевому слову в словаре")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись найдена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @GetMapping("{dictionaryId}/record")
    public ShowRecordDTO searchRecordByName(
            @Parameter(description = "Ключевое слово для поиска") @RequestParam String keyword,
            @Parameter(description = "ID словаря") @PathVariable Integer dictionaryId
    ) {
        return dictionaryService.searchRecordByName(keyword, dictionaryId);
    }
}
