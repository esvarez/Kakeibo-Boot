package dev.esuarez.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder(toBuilder = true)
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
/*
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roll_id", referencedColumnName = "id")
    )
 */
    //@JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_roll",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roll_id"))
    private Set<Roll> rolls;

    private boolean active;
}
