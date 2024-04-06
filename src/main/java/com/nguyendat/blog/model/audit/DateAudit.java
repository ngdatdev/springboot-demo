package com.nguyendat.blog.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@JsonIgnoreProperties(value = {"createAt", "updateAt"}, allowGetters = true)
public abstract class DateAudit {
    @CreatedDate
    @Column(updatable = false)
    private LocalDate createAt;

    @LastModifiedDate
    private LocalDate updateAt;

}
