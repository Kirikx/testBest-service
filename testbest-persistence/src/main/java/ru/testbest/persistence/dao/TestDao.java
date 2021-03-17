package ru.testbest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Test;

@Repository
public interface TestDao extends JpaRepository<Test, String> {

}
