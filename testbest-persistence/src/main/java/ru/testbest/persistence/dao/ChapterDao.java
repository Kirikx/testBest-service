package ru.testbest.persistence.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Chapter;

@Repository
public interface ChapterDao extends JpaRepository<Chapter, UUID> {

  Optional<Chapter> findByIdAndIsDeletedFalse(UUID id);

  List<Chapter> findAllByIsDeletedFalse();

}
