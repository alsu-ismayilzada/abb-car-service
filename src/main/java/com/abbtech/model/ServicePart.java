package com.abbtech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "service_part")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "service_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_service_part_service_visit")
    )
    private ServiceVisit serviceVisit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "part_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_service_part_part")
    )
    private Part part;

    @Column(nullable = false)
    private Integer quantity;
}
