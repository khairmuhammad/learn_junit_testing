package com.example.learn_junit_testing.controller;

import com.example.learn_junit_testing.entity.Book;
import com.example.learn_junit_testing.exception.NotFoundException;
import com.example.learn_junit_testing.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book record1 = new Book(1L, "Atomic", "summary", 5);
    Book record2 = new Book(2L, "Atomic2", "summary2", 3);
    Book record3 = new Book(3L, "Atomic3", "summary3", 4);
    Book record4 = new Book(4L, "Atomic4", "summary4", 2);

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks_success() throws Exception {
        List<Book> books = new ArrayList<>(Arrays.asList(record1, record2, record3));

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Atomic3")));
    }

    @Test
    public void getBookById_success() throws Exception {
        Mockito.when(bookRepository.findById(record1.getBookId())).thenReturn(Optional.of(record1));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Atomic")));
    }

    @Test
    public void createBookRecord_success() throws Exception {
        Book book = Book.builder()
                .bookId(5L)
                .name("you can win")
                .summary("inspirational book")
                .rating(5)
                .build();
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        String content = objectWriter.writeValueAsString(book);

        MockHttpServletRequestBuilder mockPostReq = MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockPostReq)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("you can win")));
    }

    @Test
    public void updateBookRecord_success() throws Exception {
        Book updatedRecord = Book.builder()
                .bookId(1L)
                .name("updated book name")
                .summary("updated summary")
                .rating(1)
                .build();

        Mockito.when(bookRepository.findById(record1.getBookId())).thenReturn(Optional.ofNullable(record1));
        Mockito.when(bookRepository.save(updatedRecord)).thenReturn(updatedRecord);

        String updatedContent = objectWriter.writeValueAsString(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("updated book name")));
    }

    @Test
    public void deleteRecord_success() throws Exception {
        Mockito.when(bookRepository.findById(record2.getBookId())).thenReturn(Optional.ofNullable(record2));

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /*@Test
    public void deleteRecord_notfound() throws Exception {
        Mockito.when(bookRepository.findById(record2.getBookId())).thenThrow(new NotFoundException("No any record found."));

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",is("No any record found.")));
    }*/
}