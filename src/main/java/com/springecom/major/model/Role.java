package com.springecom.major.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Getter
    private String roleName;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.REMOVE)
    private List<User> users;

    // Other methods and fields for the Role class...

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
