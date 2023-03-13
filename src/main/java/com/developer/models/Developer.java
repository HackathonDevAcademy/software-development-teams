package com.developer.models;

import com.developer.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "developer")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым!")
    @Column(name = "first_name")
    private String fullName;

    @Email(message = "Не корректный email!")
    @NotBlank(message = "Email не может быть пустым!")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Пароль не может быть пустым!")
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "position")
    private String position;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @OneToMany(mappedBy = "developer")
    private List<Report> reports;

    @OneToMany(mappedBy = "developer")
    private List<Task> tasks;

}
