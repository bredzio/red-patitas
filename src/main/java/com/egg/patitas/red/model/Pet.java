package com.egg.patitas.red.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String photo;

    @Column(nullable = false)
    private Boolean enabled;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Animal animal;

    @Column
    @CreatedDate
    private LocalDateTime dateCreated;

    @Column
    @LastModifiedDate
    private LocalDateTime dateModified;


}
