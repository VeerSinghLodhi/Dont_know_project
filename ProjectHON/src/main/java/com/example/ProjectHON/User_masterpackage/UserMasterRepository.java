package com.example.ProjectHON.User_masterpackage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterRepository extends JpaRepository<UserMaster,Long> {

    UserMaster findByEmailAndPassword(String email,String password);
}
