package dev.esuarez.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder(toBuilder = true)
@Data
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

    @Size(max = 100)
    @NotNull(message = "You should provide an email.")
    @Email(message = "Email must be a valid email address.")
    @Column(unique = true)
    private String email;

    @NotNull(message = "You should provide a password.")
    @Size(max = 250)
    private String password;

    private boolean active;
}
