package com.microservices.licensingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Entity
@ToString
@Table(name = "licenses")
public class License extends RepresentationModel<License> {

    @Id
    @JsonIgnoreProperties
    @JsonProperty("licenseId")
    @Column(name = "license_id", nullable = false)
    private String licenseId;

    @JsonProperty("description")
    @Column(name="description", nullable = false)
    private String description;

    @JsonProperty("organizationId")
    @Column(name="organization_id", nullable = false)
    private String organizationId;

    @JsonProperty("productName")
    @Column(name="product_name", nullable = false)
    private String productName;

    @JsonProperty("licenseType")
    @Column(name="license_type", nullable = false)
    private String licenseType;

    @JsonProperty("comment")
    @Column(name="comment", nullable = false)
    private String comment;

    public License withComment(String comment) {
        this.setComment(comment);
        return this;
    }
}
