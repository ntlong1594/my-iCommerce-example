package com.icommerce.developer.historical.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Historical.
 */
@Document(collection = "historical")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "historical")
public class Historical implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("user_id")
    private String userId;

    @NotNull
    @Field("correlation_id")
    private String correlationId;

    @NotNull
    @Field("action_id")
    private String actionId;

    @Field("action_description")
    private String actionDescription;

    @NotNull
    @Field("action_date")
    private LocalDate actionDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public Historical userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public Historical correlationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getActionId() {
        return actionId;
    }

    public Historical actionId(String actionId) {
        this.actionId = actionId;
        return this;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public Historical actionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
        return this;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public LocalDate getActionDate() {
        return actionDate;
    }

    public Historical actionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
        return this;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Historical)) {
            return false;
        }
        return id != null && id.equals(((Historical) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Historical{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", correlationId='" + getCorrelationId() + "'" +
            ", actionId='" + getActionId() + "'" +
            ", actionDescription='" + getActionDescription() + "'" +
            ", actionDate='" + getActionDate() + "'" +
            "}";
    }
}
