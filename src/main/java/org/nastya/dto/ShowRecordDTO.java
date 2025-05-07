package org.nastya.dto;

import lombok.Value;

@Value
public class ShowRecordDTO {
    Integer id;
    ShowDictionaryDTO dictionary;
    String name;
    String value;
}
