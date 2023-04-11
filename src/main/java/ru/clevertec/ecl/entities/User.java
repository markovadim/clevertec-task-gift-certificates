package ru.clevertec.ecl.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "email")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Order> orders;
}
