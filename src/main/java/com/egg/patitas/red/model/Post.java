package com.egg.patitas.red.model;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET enabled = false WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    @ManyToOne
    private Zone zone;

    @NotNull
    @NotEmpty
    @ManyToOne(fetch=FetchType.LAZY)
    private User user;

    @NotNull
    @NotEmpty
    private String commentary;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;

    private LocalDateTime dateLostOrFound;

    private Boolean enabled;
    private Boolean lostOrFound;

}
