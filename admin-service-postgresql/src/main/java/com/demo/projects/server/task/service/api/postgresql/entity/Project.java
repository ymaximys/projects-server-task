package com.demo.projects.server.task.service.api.postgresql.entity;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Project {

    @Id
    private Long id;

    private String name;

    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(name, project.name) &&
                Objects.equals(status, project.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }
}
