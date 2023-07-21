package ru.kpfu.itis.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.dto.request.ProjectRequest;
import ru.kpfu.itis.dto.response.ProjectResponse;
import ru.kpfu.itis.dto.response.ProjectsPage;
import ru.kpfu.itis.repositories.BlackListRepository;
import ru.kpfu.itis.services.ProjectService;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kpfu.itis.security.config.JwtSecurityConfiguration.API;

@WebMvcTest(ProjectsController.class)
@DisplayName("ProjectController is working when ...")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProjectsControllerTest {

    private static final ProjectRequest NEW_PROJECT = ProjectRequest.builder()
            .name("ML new test project").build();

    private static final String DATE = "31 MAY 2022";

    private static final ProjectResponse CREATED_PROJECT = ProjectResponse.builder()
            .id(12L).name("ML new test project").startDate(DATE).endDate(DATE).build();

    private static final ProjectRequest NEW_UPDATE_PROJECT = ProjectRequest.builder()
            .name("ML new test super project").build();

    private static final ProjectResponse UPDATED_PROJECT = ProjectResponse.builder()
            .id(12L).name("ML new test super project").startDate(DATE).endDate(DATE).build();


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BlackListRepository blackListRepository;

    @Value("${test.token}")
    private String token;

    @BeforeEach
    void setUp() {
        when(projectService.getProjects(1)).thenReturn(ProjectsPage.builder()
                .projects(Collections.singletonList(CREATED_PROJECT)).totalPages(2).build());

        when(projectService.addProject(NEW_PROJECT)).thenReturn(CREATED_PROJECT);

        when(projectService.updateProject(12L, NEW_UPDATE_PROJECT)).thenReturn(UPDATED_PROJECT);

        when(projectService.getProject(12L)).thenReturn(CREATED_PROJECT);
    }

    @Test
    public void return_403_without_token() throws Exception {
        mockMvc.perform(get(API + "/projects/2"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void add_project_with_valid_name() throws Exception {
        mockMvc.perform(post(API + "/projects")
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"ML new test project\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(12)))
                .andExpect(jsonPath("name", is("ML new test project")))
                .andExpect(jsonPath("startDate", is(DATE)))
                .andExpect(jsonPath("endDate", is(DATE)))
                .andDo(print());
    }

    @Test
    public void return_400_when_add_project_with_blank_name() throws Exception {
        mockMvc.perform(post(API + "/projects")
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void return_projects_on_1_page() throws Exception {
        mockMvc.perform(get(API + "/projects")
                .param("page", "1")
                .header(AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("projects", hasSize(1)))
                .andExpect(jsonPath("totalPages", is(2)))
                .andExpect(jsonPath("projects[0].id", is(12)))
                .andExpect(jsonPath("projects[0].name", is("ML new test project")))
                .andExpect(jsonPath("projects[0].startDate", is(DATE)))
                .andExpect(jsonPath("projects[0].endDate", is(DATE)));
    }

    @Test
    public void return_project_by_id() throws Exception {
        mockMvc.perform(get(API + "/projects/12")
                .header(AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(12)))
                .andExpect(jsonPath("name", is("ML new test project")))
                .andExpect(jsonPath("startDate", is(DATE)))
                .andExpect(jsonPath("endDate", is(DATE)))
                .andDo(print());
    }

    @Test
    public void update_project_by_id() throws Exception {
        mockMvc.perform(put(API + "/projects/12")
                .header(AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"ML new test super project\"\n" +
                        "}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("id", is(12)))
                .andExpect(jsonPath("name", is("ML new test super project")))
                .andExpect(jsonPath("startDate", is(DATE)))
                .andExpect(jsonPath("endDate", is(DATE)))
                .andDo(print());
    }


    @Test
    public void return_202_when_delete_project() throws Exception {
        mockMvc.perform(delete(API + "/projects/12")
                .header(AUTHORIZATION, token))
                .andExpect(status().isAccepted())
                .andDo(print());
    }
}
