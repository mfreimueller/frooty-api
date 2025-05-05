package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Integer> {
    boolean existsByIdAndUsers_Id(Integer groupId, Integer userId); // TODO: needs testing
}
