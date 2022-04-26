package com.demo.projects.server.task.service.api.postgresql;

import com.demo.projects.server.task.service.api.postgresql.config.FlywayConfig;
import com.demo.projects.server.task.service.api.postgresql.repository.AccountRepository;
import com.demo.projects.server.task.service.api.postgresql.repository.ProjectAccountRepository;
import com.demo.projects.server.task.service.api.postgresql.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(
        exclude = {
                R2dbcAutoConfiguration.class
        }
)
class ServicePostgresqlApplicationTests {

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private ProjectAccountRepository projectAccountRepository;

    @MockBean
    private FlywayConfig conf;

    @Test
    void contextLoads() {
    }

}
