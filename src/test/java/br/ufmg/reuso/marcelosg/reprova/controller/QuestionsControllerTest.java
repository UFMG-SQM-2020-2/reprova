package br.ufmg.reuso.marcelosg.reprova.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
class QuestionsControllerTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MockMvc mockMvc;
    //Questions tests
    @Test
    void testSingleQuestionCreation() throws Exception {
        var json = "{\"description\":\"Description\",\"statement\":\"Statement\",\"discipline\":\"Discipline\",\"themes\":[\"theme1\", \"theme2\"],\"tags\":[\"tag1\", \"tag2\"],\"pvt\":true,\"estimateTimeInMinutes\":3,\"difficulty\":\"EASY\",\"filePath\":\"File/Path\"}";
        var result = this.mockMvc.perform(post("/questions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)).andExpect(status().is2xxSuccessful()).andReturn();
        assertEquals(result.getResponse().getStatus(), status().is2xxSuccessful());
    }
    @Test
    void testGetAllQuestions() throws Exception {
        var json = "{\"description\":\"Description\",\"statement\":\"Statement\",\"discipline\":\"Discipline\",\"themes\":[\"theme1\", \"theme2\"],\"tags\":[\"tag1\", \"tag2\"],\"pvt\":true,\"estimateTimeInMinutes\":3,\"difficulty\":\"EASY\",\"filePath\":\"File/Path\"}";
        this.mockMvc.perform(post("/questions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().is2xxSuccessful());

        var result = this.mockMvc.perform(get("/questions")).andExpect(MockMvcResultMatchers.content().json("["+json+"]")).andReturn();
        assertEquals(result.getResponse().getStatus(), status().is2xxSuccessful());
    }
    @Test
    void testFindQuestionById() throws Exception {
        var json = "{\"description\":\"Description\",\"statement\":\"Statement\",\"discipline\":\"Discipline\",\"themes\":[\"theme1\", \"theme2\"],\"tags\":[\"tag1\", \"tag2\"],\"pvt\":true,\"estimateTimeInMinutes\":3,\"difficulty\":\"EASY\",\"filePath\":\"File/Path\"}";
        this.mockMvc.perform(post("/questions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)).andExpect(status().is2xxSuccessful());
        
        var result = this.mockMvc.perform(get("/questions/{{id}}")).andExpect(MockMvcResultMatchers.content().json("["+json+"]")).andReturn();
        assertEquals(result.getResponse().getStatus(), status().is2xxSuccessful());
    }
    @Test
    void testAddGradesToQuestion() throws Exception {
        var json = "{\"description\":\"Description\",\"statement\":\"Statement\",\"discipline\":\"Discipline\",\"themes\":[\"theme1\", \"theme2\"],\"tags\":[\"tag1\", \"tag2\"],\"pvt\":true,\"estimateTimeInMinutes\":3,\"difficulty\":\"EASY\",\"filePath\":\"File/Path\"}";
        var grades = "{\"year\": 2020,\"semester\": 2,\"discipline\": \"Software Reuse\",\"grades\": {\"student\": \"1\", \"grade\": 7},{\"student\": \"2\", \"grade\": 5.8},{\"student\": \"3\", \"grade\": 2},{\"student\": \"4\", \"grade\": 7},{\"student\": \"5\", \"grade\": 4.5},{\"student\": \"6\", \"grade\": 2},{\"student\": \"7\", \"grade\": 8},{\"student\": \"8\", \"grade\": 9.5}]}";
        this.mockMvc.perform(post("/questions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)).andExpect(status().is2xxSuccessful());
        var result = this.mockMvc.perform(put("/questions/{{id}}/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(grades)).andExpect(status().is2xxSuccessful()).andReturn();      
        assertEquals(result.getResponse().getStatus(), status().is2xxSuccessful());
    }
    
    //Exams tests
    @Test
    void testCreateRandomExam() throws Exception {
        var json = "{\"strategyType\": \"RANDOM\",\"totalQuestions\": 2,\"title\": \"Sample exam created with Random strategy\",\"year\": 2021,\"semester\": 2,\"saveExam\": true }";
        var result = this.mockMvc.perform(post("/exams/generator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().is2xxSuccessful()).andReturn();
        assertEquals(result.getResponse().getStatus(), status().is2xxSuccessful());
    }
    
    @Test
    void testDeleteExamById() throws Exception {
        var json = "{\"strategyType\": \"RANDOM\",\"totalQuestions\": 2,\"title\": \"Sample exam created with Random strategy\",\"year\": 2021,\"semester\": 2,\"saveExam\": true }";
        this.mockMvc.perform(post("/exams/generator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().is2xxSuccessful());
        var result = this.mockMvc.perform(delete("/exams/{id}")).andExpect(status().is2xxSuccessful()).andReturn();
        assertEquals(result.getResponse().getStatus(), status().is2xxSuccessful());
    }
    
    @Test
    void testFindExamById() throws Exception {
        var json = "{\"strategyType\": \"RANDOM\",\"totalQuestions\": 2,\"title\": \"Sample exam created with Random strategy\",\"year\": 2021,\"semester\": 2,\"saveExam\": true }";
        this.mockMvc.perform(post("/exams/generator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().is2xxSuccessful());
        var result = this.mockMvc.perform(get("/exams/{id}")).andExpect(status().is2xxSuccessful()).andReturn();
        assertEquals(result.getResponse().getStatus(), status().is2xxSuccessful());
    }
    
    @Test
    void testGetAllExams() throws Exception {
        var json = "{\"strategyType\": \"RANDOM\",\"totalQuestions\": 2,\"title\": \"Sample exam created with Random strategy\",\"year\": 2021,\"semester\": 2,\"saveExam\": true }";
        this.mockMvc.perform(post("/exams/generator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().is2xxSuccessful());
        var result = this.mockMvc.perform(get("/exams")).andExpect(status().is2xxSuccessful()).andReturn();
        assertEquals(result.getResponse().getStatus(), status().is2xxSuccessful());
    }
    
    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
    }

}