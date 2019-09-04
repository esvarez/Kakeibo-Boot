package dev.esuarez.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(unique = true)
    private String user;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 250)
    private String password;

}
