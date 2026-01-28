package com.abbtech.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_visit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Car car;

    @Column(name = "service_date", nullable = false)
    LocalDateTime serviceDate;

    @Column(name = "odometer_km", nullable = false)
    Integer odometerKm;

    @Column(name = "service_type", nullable = false)
    String serviceType;

    String notes;

}
