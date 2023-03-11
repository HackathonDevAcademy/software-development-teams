package com.developer.models;

import lombok.*;

import javax.persistence.*;
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "position")
    private String position;

    @ManyToMany(mappedBy = "developers")
    private List<Team> teams;

    @ManyToMany(mappedBy = "developers")
    private List<Project> projects;

    @OneToMany(mappedBy = "developer")
    private List<Task> tasks;
}
