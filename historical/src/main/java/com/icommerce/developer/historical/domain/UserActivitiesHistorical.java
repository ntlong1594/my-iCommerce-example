package com.icommerce.developer.historical.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Historical.
 */
@Document(collection = "historical_user_activities")
public class UserActivitiesHistorical implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("user_id")
    private String userId;

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

    public UserActivitiesHistorical userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActionId() {
        return actionId;
    }

    public UserActivitiesHistorical actionId(String actionId) {
        this.actionId = actionId;
        return this;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public UserActivitiesHistorical actionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
        return this;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public LocalDate getActionDate() {
        return actionDate;
    }

    public UserActivitiesHistorical actionDate(LocalDate actionDate) {
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
        if (!(o instanceof UserActivitiesHistorical)) {
            return false;
        }
        return id != null && id.equals(((UserActivitiesHistorical) o).id);
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
            ", actionId='" + getActionId() + "'" +
            ", actionDescription='" + getActionDescription() + "'" +
            ", actionDate='" + getActionDate() + "'" +
            "}";
    }
}
