package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.History;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends CrudRepository<History, Integer> {
    Optional<List<History>> findByGroup(Group group);
}
