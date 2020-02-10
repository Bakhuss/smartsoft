package ru.bakhuss.smartsoft.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;
    @Version
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Integer version;
    String name;
    @ManyToMany(mappedBy = "roles")
    Set<User> users;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "{id:" + getId() +
                ";name:" + getName() +
                ";users size:" + (users.isEmpty() ? 0 : getUsers().size()) +
                "}";
    }
}
