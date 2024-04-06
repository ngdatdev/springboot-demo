package com.nguyendat.blog.model.audit;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
@Data
@JsonIgnoreProperties(
        value = { "createdBY", "updatedBy" },
        allowGetters = true
)
public abstract class UserDateAudit extends DateAudit{
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(updatable = false)
    private String createBy;

    @LastModifiedBy
    private String updateBy;
}