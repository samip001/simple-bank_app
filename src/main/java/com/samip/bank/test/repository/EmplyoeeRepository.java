package com.samip.bank.test.repository;

import com.samip.bank.test.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmplyoeeRepository extends JpaRepository<Employee,Long> {

    @Query("SELECT e from Employee e where e.email = ?1")
    Employee checkEmailExits(String email);
}
