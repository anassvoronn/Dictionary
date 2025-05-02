package org.nastya.dto;

import lombok.Value;
import org.nastya.entity.Dictionary;

@Value
public class RecordsDTO {
    Integer id;
    Dictionary dictionaryId;
    String name;
    String value;
}
