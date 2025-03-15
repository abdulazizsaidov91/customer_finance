package com.is.customerfinance.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles", schema = "finance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

}
