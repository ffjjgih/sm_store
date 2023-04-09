package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper,Integer> {
    @Query("SELECT s FROM Shipper s WHERE s.transportCompanyId = :transportCompanyId")
    List<Shipper> findByTransportCompanyId(@Param("transportCompanyId") int transportCompanyId);

    @Query("SELECT s FROM Shipper s WHERE s.shipperCode = :shipperCode")
    Shipper findByShipperCode(@Param("shipperCode") String transportCompanyId);
}
