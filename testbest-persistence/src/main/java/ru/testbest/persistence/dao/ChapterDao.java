package ru.testbest.persistence.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Chapter;

@Repository
public interface ChapterDao extends JpaRepository<Chapter, String> {

  Optional<Chapter> findByIdAndIsDeletedFalse(String uuid);

  List<Chapter> findAllByIsDeletedFalse();

}
