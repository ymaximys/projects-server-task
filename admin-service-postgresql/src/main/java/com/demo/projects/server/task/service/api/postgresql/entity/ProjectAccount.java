package com.demo.projects.server.task.service.api.postgresql.entity;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class ProjectAccount {

    @Id
    private Long id;

    private Long projectId;

    private Long accountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectAccount that = (ProjectAccount) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, accountId);
    }
}
