package ru.clevertec.ecl.entities;


import jakarta.persistence.*;
import lombok.*;
import ru.clevertec.ecl.mapping.DurationConverter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tag_certificate",
            joinColumns = {@JoinColumn(name = "certificate_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @ToString.Exclude
    private List<Tag> tags;

    @ManyToMany(mappedBy = "certificates")
    @ToString.Exclude
    private Set<Order> orders;

    public Certificate(String name, String description, double price, Duration duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }
}
