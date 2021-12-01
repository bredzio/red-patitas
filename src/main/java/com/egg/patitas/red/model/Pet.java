package com.egg.patitas.red.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pets")
@SQLDelete(sql = "UPDATE pets SET enabled = false WHERE id = ?")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    private String name;

    @JoinColumn(nullable = false)
    @NotNull
    @NotEmpty
    private String photo;

    @NotNull
    @NotEmpty
    private Boolean enabled;

    @NotNull
    @NotEmpty
    @ManyToOne(fetch=FetchType.LAZY)
    private Animal animal;

    @NotNull
    @NotEmpty
    @ManyToOne(fetch=FetchType.LAZY)
    private User user;

    @CreatedDate
    @Column (updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;


}
