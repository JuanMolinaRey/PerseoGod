package com.Perseo.controller;

import com.Perseo.exception.ResourceNotFoundException;
import com.Perseo.model.Course;
import com.Perseo.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

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
        course.setTitle("Java Programming");
        course.setDescription("Learn Java from scratch");
        course.setPrice(99.99);
    }

    @Test
    public void test_Add_Course() throws Exception {
        when(courseService.saveCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/courses/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Java Programming\",\"description\":\"Learn Java from scratch\",\"price\":99.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Programming"));
    }

    @Test
    public void test_Update_Course() throws Exception {
        Course updatedCourse = new Course();
        updatedCourse.setId(1L);
        updatedCourse.setTitle("Advanced Java Programming");
        updatedCourse.setDescription("Learn advanced Java concepts");
        updatedCourse.setPrice(129.99);

        when(courseService.findById(1L)).thenReturn(course);
        when(courseService.saveCourse(any(Course.class))).thenReturn(updatedCourse);

        mockMvc.perform(put("/courses/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Advanced Java Programming\",\"description\":\"Learn advanced Java concepts\",\"price\":129.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Advanced Java Programming"));
    }


    @Test
    public void test_Delete_Course() throws Exception {
        when(courseService.findById(1L)).thenReturn(course);
        doNothing().when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/courses/delete/1"))
                .andExpect(status().isNoContent());
    }


    @Test
    public void test_Get_Course_By_Id() throws Exception {
        when(courseService.findById(1L)).thenReturn(course);

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Programming"));
    }


    @Test
    public void test_Get_All_Courses() throws Exception {
        List<Course> courses = Arrays.asList(course);
        when(courseService.findAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/courses/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Java Programming"));
    }
}
