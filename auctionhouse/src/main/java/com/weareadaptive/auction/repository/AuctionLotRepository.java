package com.weareadaptive.auction.repository;

import com.weareadaptive.auction.model.AuctionLot;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuctionLotRepository extends JpaRepository<AuctionLot, Integer> {

  @Query("select a from AuctionLot a where a.id=?1")
  Optional<AuctionLot> get(int id);

  @Query("select a from AuctionLot a")
  Collection<AuctionLot> all();

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("update AuctionLot a "
      + "set a.totalSoldQuantity = :totalSoldQuantity, "
      +     "a.totalRevenue = :totalRevenue, "
      +     "a.closingTime = :closingTime "
      + "where a.id = :id")
  void close(
      @Param("id") int id,
      @Param("totalSoldQuantity") int totalSoldQuantity,
      @Param("totalRevenue")BigDecimal totalRevenue,
      @Param("closingTime") Instant closingTime);
}
