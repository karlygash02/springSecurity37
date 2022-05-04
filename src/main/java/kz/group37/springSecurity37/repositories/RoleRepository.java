package kz.group37.springSecurity37.repositories;

import kz.group37.springSecurity37.model.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<AuthRole, Long> {
}
