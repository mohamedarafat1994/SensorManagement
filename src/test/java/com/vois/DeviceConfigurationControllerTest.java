package com.vois;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SqlGroup({
    @Sql(value = "classpath:reset.sql", executionPhase = BEFORE_TEST_METHOD),
    @Sql(value = "classpath:preload.sql", executionPhase = BEFORE_TEST_METHOD)
})
public class DeviceConfigurationControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void configure() throws Exception {
		this.mockMvc.perform(post("/configuration/configure/{deviceId}",101))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(4)));
	}
	
	@Test
	public void configure_NotFound() throws Exception {
		this.mockMvc.perform(post("/configuration/configure/{deviceId}",111))
        .andDo(print())
        .andExpect(status().isNoContent());
	}
}
