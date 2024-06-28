package com.example.demo;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
//@AutoConfigureMockMvc
public class HttpRequestTest{

}
	
//	@Autowired
//	private MockMvc mockMvc;
//	
//	   @Test
//	    public void getAllTest() throws Exception {
//		   
//		   String tokenValue=
//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyIiwiZXhwIjoxNzE5MjM3NTYzLCJlIjoiZ2lvZ2lvQGxhbWlhbWFpbC5pdCIsImEiOlsiUk9MRV9BRE1JTiJdfQ.BCK04uytNBZ5H6J6BxjQnpGMiMGWL_K3yuU1fz1ss5k";		   
//		   
//		   //mockMvc.perform(get("/getAll"))
////	        
////
////	               .andExpect(status().isFound())
////	               .andExpect(jsonPath( "$",hasSize(greaterThan(0))))
////	               .andExpect(jsonPath("$[0].id", notNullValue()))
////	               .andExpect(jsonPath("$[0].name", notNullValue()));
//	        
//	        mockMvc.perform(get("/getAll")
//	                .header("Authorization", "Bearer "+tokenValue))
//	                .andExpect(status().isFound())
//	                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
//	                .andExpect(jsonPath("$[0].id", notNullValue()))
//	                .andExpect(jsonPath("$[0].name", notNullValue()));
//	    }
//
//	
//}

//FIRST WAY
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//public class HttpRequestTest {
//    
//    @LocalServerPort
//    private int port;
//    
//    @Autowired
//    private TestRestTemplate restTemplate;
//    
//    @Test
//    void greetingShouldReturnDefaultMessage() throws Exception {
//        ResponseEntity<Person[]> responseEntity = this.restTemplate
//            .getForEntity("http://localhost:" + port + "/getAll", Person[].class);
//        
//        // Verifica il codice di stato HTTP
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//
//        // Verifica che la lista non sia vuota (opzionale)
//        Person[] persons = responseEntity.getBody();
//        assertThat(persons).isNotEmpty();
//    }
//}