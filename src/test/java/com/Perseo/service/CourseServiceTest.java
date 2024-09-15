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
        course.setTitle("Java Programming");
        course.setDescription("Learn Java from scratch");
        course.setPrice(99.99);
    }

    @Test
    public void testSaveCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course savedCourse = courseService.saveCourse(course);

        assertNotNull(savedCourse);
        assertEquals("Java Programming", savedCourse.getTitle());
    }

    @Test
    public void testFindById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course foundCourse = courseService.findById(1L);

        assertNotNull(foundCourse);
        assertEquals("Java Programming", foundCourse.getTitle());
    }

    @Test
    public void testFindById_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Course foundCourse = courseService.findById(1L);

        assertNull(foundCourse);
    }

    @Test
    public void testDeleteCourse() {
        doNothing().when(courseRepository).deleteById(1L);

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAllCourses() {
        List<Course> courses = Arrays.asList(course);
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> allCourses = courseService.findAllCourses();

        assertNotNull(allCourses);
        assertEquals(1, allCourses.size());
        assertEquals("Java Programming", allCourses.get(0).getTitle());
    }
}
