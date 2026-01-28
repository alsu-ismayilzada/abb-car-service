package com.abbtech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "path_pattern", nullable = false)
    private String pathPattern;

    @Column(name = "permission_code", nullable = false)
    private String permissionCode;

    @ManyToMany(mappedBy = "permissions")
    List<Role> roles = new ArrayList<>();
}
