package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.util.TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * Integration test template to get you started. Add tests and make modifications as you see fit.
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MatchControllerIntegrationTest {

    private static final String COMBATLOG_FILE_1 = "/data/combatlog_1.log.txt";
    private static final String COMBATLOG_FILE_2 = "/data/combatlog_2.log.txt";

    @Autowired
    private MockMvc mvc;

    private Map<String, Long> matchIds;

    @BeforeAll
    void setup() throws Exception {
        // Populate the database with all events from both sample data files and store the returned
        // match IDS.
        matchIds = Map.of(
                COMBATLOG_FILE_1, ingestMatch(COMBATLOG_FILE_1),
                COMBATLOG_FILE_2, ingestMatch(COMBATLOG_FILE_2));
    }

    @Test
    void someTest() {
        assertThat(mvc).isNotNull();
    }

    @Test
    void testValidHeroKill() throws Exception {
        mvc
                .perform(get("/api/match/{matchId}", matchIds.get(COMBATLOG_FILE_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].hero").value(TestConstants.DOTA_HERO_ABYSSAL_UNDERLORD))
                .andExpect(jsonPath("$[0].kills").value(TestConstants.DOTA_HERO_ABYSSAL_UNDERLORD_KILLS))
                .andExpect(jsonPath("$[1].hero").value(TestConstants.DOTA_HERO_BANE))
                .andExpect(jsonPath("$[1].kills").value(TestConstants.DOTA_HERO_BANE_KILLS));
    }

    @Test
    void testValidHeroItems() throws Exception {
        mvc
                .perform(get("/api/match/{matchId}/{hero_name}/items", matchIds.get(COMBATLOG_FILE_1), TestConstants.DOTA_HERO_BANE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].item").value(TestConstants.DOTA_HERO_BANE_ITEM))
                .andExpect(jsonPath("$[0].timestamp").isNotEmpty())
                .andExpect(jsonPath("$[0].timestamp").isNumber());
    }

    @Test
    void testValidHeroSpell() throws Exception {
        mvc
                .perform(get("/api/match/{matchId}/{hero_name}/spells", matchIds.get(COMBATLOG_FILE_1), TestConstants.DOTA_HERO_BANE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].spell").value(TestConstants.DOTA_HERO_BANE_SPELLS))
                .andExpect(jsonPath("$[0].casts").value(TestConstants.DOTA_HERO_BANE_SPELL_CAST));
    }

    @Test
    void testValidHeroDamage() throws Exception {
        mvc
                .perform(get("/api/match/{matchId}/{hero_name}/damage", matchIds.get(COMBATLOG_FILE_1), TestConstants.DOTA_HERO_BANE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].target").value(TestConstants.DOTA_HERO_ABYSSAL_UNDERLORD))
                .andExpect(jsonPath("$[0].damage_instances").value(TestConstants.DOTA_HERO_BANE_DAMAGE_INSTANCES))
                .andExpect(jsonPath("$[0].total_damage").value(TestConstants.DOTA_HERO_BANE_TOTAL_DAMAGE));
    }

    /**
     * Helper method that ingests a combat log file and returns the match id associated with all parsed events.
     *
     * @param file file path as a classpath resource, e.g.: /data/combatlog_1.log.txt.
     * @return the id of the match associated with the events parsed from the given file
     * @throws Exception if an error happens when reading or ingesting the file
     */
    private Long ingestMatch(String file) throws Exception {
        String fileContent = IOUtils.resourceToString(file, StandardCharsets.UTF_8);

        return Long.parseLong(mvc.perform(post("/api/match")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(fileContent))
                .andReturn()
                .getResponse()
                .getContentAsString());
    }
}
