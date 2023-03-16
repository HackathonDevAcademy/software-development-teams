package com.developer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "team")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя команды не может быть пустым")
    @Max(value = 25, message = "Превышено максимальное значение для имени команды!")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<Developer> developers;
}
