package org.nastya.service.mapper;

import lombok.RequiredArgsConstructor;
import org.nastya.dto.ShowDictionaryDTO;
import org.nastya.entity.Dictionary;
import org.nastya.service.DictionaryMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DictionaryMapperImpl implements DictionaryMapper {

    @Override
    public List<ShowDictionaryDTO> mapToDTO(List<Dictionary> dictionaries) {
        Assert.notNull(dictionaries, "Dictionaries must not be null");
        List<ShowDictionaryDTO> result = new ArrayList<>();
        for (Dictionary dictionary : dictionaries) {
            result.add(mapToDTO(dictionary));
        }
        return result;
    }

    public ShowDictionaryDTO mapToDTO(Dictionary dictionary) {
        Assert.notNull(dictionary, "Dictionary must not be null");
        return new ShowDictionaryDTO(dictionary.getId(), dictionary.getName());
    }
}
