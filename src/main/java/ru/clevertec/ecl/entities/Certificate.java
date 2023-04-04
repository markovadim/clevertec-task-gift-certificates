package ru.clevertec.ecl.entities;


import lombok.*;
import ru.clevertec.ecl.mapping.DurationConverter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "certificates")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "createDate"})
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;

    @Convert(converter = DurationConverter.class)
    private Duration duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(
            name = "tag_certificate",
            joinColumns = {@JoinColumn(name = "certificateid")},
            inverseJoinColumns = {@JoinColumn(name = "tagid")}
    )
    @ToString.Exclude
    private List<Tag> tags;

    public Certificate(String name, String description, double price, Duration duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }
}
