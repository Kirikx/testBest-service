package ru.testbest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Topic;

@Repository
public interface TopicDao extends JpaRepository<Topic, String> {

}
