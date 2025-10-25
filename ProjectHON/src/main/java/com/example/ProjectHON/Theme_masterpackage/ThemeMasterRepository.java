package com.example.ProjectHON.Theme_masterpackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThemeMasterRepository extends JpaRepository<ThemeMaster,Long> {

    @Query("select th from ThemeMaster th where th.themeName =:themeName")
    ThemeMaster getThemeByThemeName(@Param("themeName")String themeName);
}
