package com.thesis.alert_service.repository;

import com.thesis.alert_service.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    @Query(value = "SELECT * FROM zones WHERE ST_Contains(geometry, ST_SetSRID(ST_Point(:lon, :lat), 4326))",
            nativeQuery = true)
    List<Zone> findZonesContainingPoint(@Param("lat") double lat, @Param("lon") double lon);
}
