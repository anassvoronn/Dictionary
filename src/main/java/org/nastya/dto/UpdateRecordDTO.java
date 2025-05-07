package org.nastya.dto;

import lombok.Value;

@Value
public class UpdateRecordDTO {
    Integer id;
    ShowDictionaryDTO dictionary;
    String name;
    String value;
}
