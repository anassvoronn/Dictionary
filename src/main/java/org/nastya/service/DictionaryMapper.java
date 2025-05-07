package org.nastya.service;

import org.nastya.dto.ShowDictionaryDTO;
import org.nastya.entity.Dictionary;

import java.util.List;

public interface DictionaryMapper {

    List<ShowDictionaryDTO> mapToDTO(List<Dictionary> dictionaries);

    ShowDictionaryDTO mapToDTO(Dictionary dictionary);
}
