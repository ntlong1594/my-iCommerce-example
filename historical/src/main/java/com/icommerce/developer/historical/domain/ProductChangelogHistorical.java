package com.icommerce.developer.historical.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

/**
 * A Historical.
 */
@Document(collection = "historical_product_changelog")
public class ProductChangelogHistorical implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String productId;

    @NotNull
    @Field("version")
    private Integer version;

    @NotNull
    @Field("detail")
    private Map<String, Object> detail;

    @NotNull
    @Field("action_date")
    private LocalDate updatedDate;

    @NotNull
    @Field("updated_by")
    private String updatedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "ProductChangelogHistorical{" +
            "id='" + id + '\'' +
            ", productId='" + productId + '\'' +
            ", version=" + version +
            ", detail=" + detail +
            ", updatedDate=" + updatedDate +
            ", updatedBy='" + updatedBy + '\'' +
            '}';
    }
}
