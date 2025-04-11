//package com.sparkplug.catalog.controller;
//
//import com.sparkplug.catalog.dto.manufacturer.ManufacturerCreateRequestDto;
//import com.sparkplug.catalog.dto.manufacturer.ManufacturerResponseDto;
//import com.sparkplug.catalog.mapper.ManufacturerMapper;
//import com.sparkplug.catalog.service.ManufacturersService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class ManufacturersControllerTests {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private ManufacturersService service;
//
//    @Mock
//    private ManufacturerMapper mapper;
//
//    @InjectMocks
//    private ManufacturersController controller;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//    }
//
//    @Test
//    void testGetAll() throws Exception {
//        var manufacturerDto = new ManufacturerResponseDto(1L, "Toyota", "Japan");
//        when(service.getAll()).thenReturn(List.of());
//        when(mapper.toResponseDto(any())).thenReturn(manufacturerDto);
//
//        mockMvc.perform(get("/manufacturers"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testGetById() throws Exception {
//        var manufacturerDto = new ManufacturerResponseDto(1L, "Toyota", "Japan");
//        when(service.getById(1L)).thenReturn(null);
//        when(mapper.toResponseDto(any())).thenReturn(manufacturerDto);
//
//        mockMvc.perform(get("/manufacturers/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Toyota"))
//                .andExpect(jsonPath("$.country").value("Japan"));
//    }
//
//    @Test
//    void testCreate() throws Exception {
//        var requestDto = new ManufacturerCreateRequestDto("Toyota", "Japan");
//        when(service.create("Toyota", "Japan")).thenReturn(1L);
//
//        mockMvc.perform(post("/manufacturers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                            {"name": "Toyota", "country": "Japan"}
//                        """))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "/manufacturers/1"));
//    }
//
//    @Test
//    void testDelete() throws Exception {
//        doNothing().when(service).delete(1L);
//
//        mockMvc.perform(delete("/manufacturers/1"))
//                .andExpect(status().isOk());
//    }
//}
