package com.Perseo.service;

import com.Perseo.model.Course;
import com.Perseo.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private ICourseRepository iCourseRepository;

    public Course saveCourse(Course course) {
        return iCourseRepository.save(course);
    }

    public Course findById(Long id) {
        return iCourseRepository.findById(id).orElse(null);
    }

    public void deleteCourse(Long id) {
        iCourseRepository.deleteById(id);
    }

    public List<Course> findAllCourses() {
        return iCourseRepository.findAll();
    }
}

