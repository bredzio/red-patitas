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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@SQLDelete(sql = "UPDATE users SET enabled = false WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String lastname;

    @NotNull
    @NotEmpty
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn(name = "roleId")
    private Role role;

    @NotNull
    @NotEmpty
    @Email(message = "Email should be valid.", regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    @Size(min = 5, max = 15)
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private Boolean enabled = true;

    @NotNull
    @NotEmpty
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @NotNull
    @NotEmpty
    @LastModifiedDate
    private LocalDateTime dateModified;

   @Nullable
   @OneToMany (mappedBy = "user")
    private List<Post> posts;

    @OneToMany (mappedBy = "user")
    @Nullable
    private List<Pet> pets;


}


