package com.vois;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;



@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

class WarehouseInventoryControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Test
	@SqlGroup({
	    @Sql(value = "classpath:reset.sql", executionPhase = BEFORE_TEST_METHOD)
	})
	void create() throws Exception {
		final File jsonFile = new ClassPathResource("new-device.json").getFile();
        final String deviceToCreate = Files.readString(jsonFile.toPath());
        this.mockMvc.perform(post("/inventory/create")
                .contentType(APPLICATION_JSON)
                .content(deviceToCreate))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(4)));
	}

	@Test
	@SqlGroup({
	    @Sql(value = "classpath:reset.sql", executionPhase = BEFORE_TEST_METHOD),
	    @Sql(value = "classpath:preload.sql", executionPhase = BEFORE_TEST_METHOD)
	})
	void getAll() throws Exception {
		this.mockMvc.perform(get("/inventory/devices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	@SqlGroup({
	    @Sql(value = "classpath:reset.sql", executionPhase = BEFORE_TEST_METHOD),
	    @Sql(value = "classpath:preload.sql", executionPhase = BEFORE_TEST_METHOD)
	})
	void deleteDevice_NotFound () throws Exception {
		this.mockMvc.perform(delete("/inventory/device/{deviceId}",111))
        .andDo(print())
        .andExpect(status().isNoContent());
	}
	
	@Test
	@SqlGroup({
	    @Sql(value = "classpath:reset.sql", executionPhase = BEFORE_TEST_METHOD),
	    @Sql(value = "classpath:preload.sql", executionPhase = BEFORE_TEST_METHOD)
	})
	void deleteDevice () throws Exception {
		this.mockMvc.perform(delete("/inventory/device/{deviceId}",101))
        .andDo(print())
        .andExpect(status().isOk());
	}
}
