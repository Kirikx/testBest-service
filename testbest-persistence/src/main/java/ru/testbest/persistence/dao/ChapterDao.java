package ru.testbest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Chapter;

@Repository
public interface ChapterDao extends JpaRepository<Chapter, String> {

}
