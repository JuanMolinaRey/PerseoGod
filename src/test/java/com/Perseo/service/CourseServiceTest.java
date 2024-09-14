package com.Perseo.service;

import com.Perseo.model.Course;
import com.Perseo.repository.ICourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private ICourseRepository courseRepository;

    private Course course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        course = new Course();
        course.setId(1L);
        course.setTitle("Java Basics");
        course.setDescription("Introduction to Java");
        course.setPrice(99.99);
    }

    @Test
    public void testSaveCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course savedCourse = courseService.saveCourse(course);

        assertNotNull(savedCourse);
        assertEquals("Java Basics", savedCourse.getTitle());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testFindById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course foundCourse = courseService.findById(1L);

        assertNotNull(foundCourse);
        assertEquals(1L, foundCourse.getId());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_CourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Course foundCourse = courseService.findById(1L);

        assertNull(foundCourse);
    }

    @Test
    public void testDeleteCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAllCourses() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        List<Course> courses = courseService.findAllCourses();

        assertFalse(courses.isEmpty());
        assertEquals(1, courses.size());
        verify(courseRepository, times(1)).findAll();
    }
}
