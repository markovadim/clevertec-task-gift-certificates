package ru.clevertec.ecl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "orders")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "number")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long number;
    @Column(name = "order_price")
    private double orderPrice;
    @Column(name = "buy_date")
    private LocalDateTime buyDate;

    @ManyToMany
    @JoinTable(
            name = "order_certificate",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    @ToString.Exclude
    private Set<Certificate> certificates;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
