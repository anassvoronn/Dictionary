package org.nastya.repository;

import org.nastya.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    Optional<Record> findByNameAndDictionaryId(String keyword, Integer dictionaryId);
}
