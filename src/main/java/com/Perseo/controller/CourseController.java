package com.Perseo.controller;

import com.Perseo.exception.ResourceNotFoundException;
import com.Perseo.model.Course;
import com.Perseo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public Course addCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @PutMapping("/update/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course existingCourse = courseService.findById(id);
        if (existingCourse != null) {
            existingCourse.setTitle(course.getTitle());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setPrice(course.getPrice());
            return courseService.saveCourse(existingCourse);
        }
        throw new ResourceNotFoundException("Course not found with id: " + id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        Course course = courseService.findById(id);
        if (course != null) {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        }
        throw new ResourceNotFoundException("Course not found with id: " + id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        Course course = courseService.findById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        }
        throw new ResourceNotFoundException("Course not found with id: " + id);
    }

    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseService.findAllCourses();
    }
}

