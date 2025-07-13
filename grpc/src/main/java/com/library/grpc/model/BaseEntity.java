package com.library.grpc.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

//    @Version
//    private int version;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @PreUpdate
    void onPersist() {
        this.setUpdatedDate(new Date());
    }
}
