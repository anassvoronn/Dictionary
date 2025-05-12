package org.nastya.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dictionary_id", nullable = false)
    private Integer dictionaryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(id, record.id) && Objects.equals(dictionaryId, record.dictionaryId) && Objects.equals(name, record.name) && Objects.equals(value, record.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictionaryId, name, value);
    }
}
