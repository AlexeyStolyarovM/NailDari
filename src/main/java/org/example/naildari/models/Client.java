package org.example.naildari.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "clients",schema = "nail")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String firstName;
    @Column
    private String phone;
    @OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @ToString.Exclude
    private List<Appointment> appointments;
}
