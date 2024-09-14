package com.Perseo.controller;

import com.Perseo.model.Course;
import com.Perseo.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CourseControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseService;

    private Course course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();


        course = new Course();
        course.setId(1L);
        course.setTitle("Java Basics");
        course.setDescription("Introduction to Java");
        course.setPrice(99.99);
    }

    @Test
    public void testAddCourse() throws Exception {
        when(courseService.saveCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/courses/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Java Basics\",\"description\":\"Introduction to Java\",\"price\":99.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics"));
    }

    @Test
    public void testUpdateCourse() throws Exception {
        when(courseService.findById(1L)).thenReturn(course);
        when(courseService.saveCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(put("/courses/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Advanced Java\",\"description\":\"Deep dive into Java\",\"price\":199.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Advanced Java"));
    }

    @Test
    public void testUpdateCourse_NotFound() throws Exception {
        when(courseService.findById(1L)).thenReturn(null);

        mockMvc.perform(put("/courses/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Advanced Java\",\"description\":\"Deep dive into Java\",\"price\":199.99}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCourse() throws Exception {
        when(courseService.findById(1L)).thenReturn(course);

        mockMvc.perform(delete("/courses/delete/1"))
                .andExpect(status().isNoContent());

        verify(courseService, times(1)).deleteCourse(1L);
    }

    @Test
    public void testDeleteCourse_NotFound() throws Exception {
        when(courseService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/courses/delete/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCourse() throws Exception {
        when(courseService.findById(1L)).thenReturn(course);

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics"));
    }

    @Test
    public void testGetCourse_NotFound() throws Exception {
        when(courseService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllCourses() throws Exception {
        when(courseService.findAllCourses()).thenReturn(Arrays.asList(course));

        mockMvc.perform(get("/courses/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Java Basics"));
    }
}
