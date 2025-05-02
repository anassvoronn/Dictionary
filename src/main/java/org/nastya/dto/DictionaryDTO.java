package org.nastya.dto;

import lombok.Value;

import java.util.List;

@Value
public class DictionaryDTO {
    Integer id;
    String name;
    List<RecordsDTO> entries;
}
