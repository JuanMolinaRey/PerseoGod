package com.Perseo.repository;

import com.Perseo.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByUserId(Long userId);
}
