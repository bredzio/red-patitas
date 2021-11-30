package com.egg.patitas.red.model;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts p SET p.enabled = false WHERE p.id = ?")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Zone zone;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch=FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String commentary;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;

    @LastModifiedDate
    private LocalDateTime dateLostOrFound;

    private Boolean enabled;
    private Boolean lostOrFound;

}
