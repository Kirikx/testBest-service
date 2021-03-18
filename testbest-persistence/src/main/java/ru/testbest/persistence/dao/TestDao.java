package ru.testbest.persistence.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Test;

@Repository
public interface TestDao extends JpaRepository<Test, String> {

  Optional<Test> findByIdAndIsDeletedFalse(String uuid);

  List<Test> findAllByIsDeletedFalse();

}
