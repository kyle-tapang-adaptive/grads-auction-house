package com.weareadaptive.auction.repository;

import com.weareadaptive.auction.model.Bid;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
  List<Bid> findBidsByAuctionLotId(int id);

  @Query("select b from Bid b where b.id=?1 and b.state='WIN'")
  List<Bid> getWinningBidsByAuctionLotId(int id);
}
