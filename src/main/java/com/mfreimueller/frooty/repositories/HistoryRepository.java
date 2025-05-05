package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.HistoryEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends CrudRepository<HistoryEntry, Integer> {
    Optional<List<HistoryEntry>> findByGroup(Group group);
}
