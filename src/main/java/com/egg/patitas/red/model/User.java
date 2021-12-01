package com.egg.patitas.red.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@SQLDelete(sql = "UPDATE users SET enabled = false WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Lastname cannot be null")
    private String lastname;


    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn(name = "roleId")
    private Role role;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid.", regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    @Size(min = 5, max = 15)
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull
    private Boolean enabled = true;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;

   @Nullable
   @OneToMany (mappedBy = "user")
    private List<Post> posts;

    @OneToMany (mappedBy = "user")
    @Nullable
    private List<Pet> pets;

    @ManyToOne
    @JoinColumn(nullable=false)
    private Role rol;


}


