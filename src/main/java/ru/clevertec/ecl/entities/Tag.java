package ru.clevertec.ecl.entities;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "tags")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private List<Certificate> certificateList;

    public Tag(String name) {
        this.name = name;
    }
}
