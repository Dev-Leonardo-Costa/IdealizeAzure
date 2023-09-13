package com.idealize.repository;

import com.idealize.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepostory extends JpaRepository<Person, Long> {
}
