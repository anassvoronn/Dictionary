package org.nastya.dto;

import lombok.Value;

@Value
public class UpdateRecordDTO {
    Integer id;
    Integer dictionaryId;
    String name;
    String value;
}
