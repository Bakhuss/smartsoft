package ru.bakhuss.smartsoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bakhuss.smartsoft.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
}
