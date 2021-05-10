package com.inthebytes.searchservice.dao;

import com.inthebytes.searchservice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationDao extends JpaRepository<Location, Long> {

}
