package com.weareadaptive.auction.service;

import static java.lang.Math.min;
import static java.lang.String.format;
import static java.math.BigDecimal.valueOf;
import static java.util.Collections.reverseOrder;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

import com.weareadaptive.auction.exception.ItemDoesNotExistException;
import com.weareadaptive.auction.model.AuctionLot;
import com.weareadaptive.auction.model.Bid;
import com.weareadaptive.auction.model.BusinessException;
import com.weareadaptive.auction.model.ClosingSummary;
import com.weareadaptive.auction.model.WinningBid;
import com.weareadaptive.auction.repository.AuctionLotRepository;
import com.weareadaptive.auction.repository.BidRepository;
import com.weareadaptive.auction.repository.UserRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class AuctionLotService {

  private final AuctionLotRepository auctionLotRepository;
  private final UserRepository userRepository;
  private final BidRepository bidRepository;

  public AuctionLotService(
      AuctionLotRepository  auctionLotRepository,
      UserRepository userRepository,
      BidRepository bidRepository) {
    this.auctionLotRepository = auctionLotRepository;
    this.userRepository = userRepository;
    this.bidRepository = bidRepository;
  }

  public AuctionLot createAuctionLot(
      String username,
      String symbol,
      double minPrice,
      int quantity) {
    String owner = userRepository.getByUsername(username)
        .orElseThrow(() -> new ItemDoesNotExistException("User does not exist"))
        .getUsername();

    AuctionLot auctionLot = new AuctionLot();
    auctionLot.setOwner(owner);
    auctionLot.setSymbol(symbol);
    auctionLot.setQuantity(quantity);
    auctionLot.setMinPrice(minPrice);
    auctionLot.setStatus(auctionLot.statusOpened());

    return auctionLotRepository.save(auctionLot);
  }

  public AuctionLot getAuctionLot(int id) {
    return auctionLotRepository.get(id)
      .orElseThrow(() -> new ItemDoesNotExistException("Auction does not exist."));
  }

  public Stream<AuctionLot> getAllAuctionLots() {
    return auctionLotRepository.all().stream();
  }

  public Bid createBid(String username, int id, double price, int quantity) {
    final String user = userRepository.getByUsername(username)
          .orElseThrow(() -> new ItemDoesNotExistException("User does not exist"))
          .getUsername();

    AuctionLot auctionLot = getAuctionLot(id);

    if (auctionLot
        .getStatus()
        .equals(auctionLot.statusClosed())) {
      throw new BusinessException("Cannot bid on a closed auction");
    }

    if (username
        .equals(auctionLot.getOwner())) {
      throw new BusinessException("User cannot bid on his own auction");
    }

    if (price < auctionLot.getMinPrice()) {
      throw new BusinessException(format("Price needs to be above %s", auctionLot.getMinPrice()));
    }

    Bid bid = new Bid();
    bid.setAuctionLotId(id);
    bid.setUsername(user);
    bid.setPrice(price);
    bid.setQuantity(quantity);
    bid.setState("PENDING");

    return bidRepository.save(bid);
  }

  public List<Bid> getBids(String username, int id) {
    if (!getAuctionLot(id)
        .getOwner()
        .equals(username)) {
      throw new BusinessException("User is not the owner of AuctionLot " + id);
    }
    return bidRepository.findBidsByAuctionLotId(id);
  }

  public ClosingSummary close(String username, int id) {
    AuctionLot auctionLot = getAuctionLot(id);
    if (!auctionLot
        .getOwner()
        .equals(username)) {
      throw new BusinessException("User is not the owner of AuctionLot " + id);
    }

    if (auctionLot
        .getStatus()
        .equals(auctionLot.statusClosed())) {
      throw new BusinessException("Cannot close because already closed.");
    }

    var orderedBids = bidRepository.findBidsByAuctionLotId(id)
        .stream()
        .sorted(reverseOrder(comparing(Bid::getPrice))
        .thenComparing(reverseOrder(comparingInt(Bid::getQuantity))))
        .toList();
    var availableQuantity = auctionLot.getQuantity();
    var revenue = BigDecimal.ZERO;
    var winningBids = new ArrayList<WinningBid>();

    for (Bid bid : orderedBids) {
      if (availableQuantity > 0) {
        var bidQuantity = min(availableQuantity, bid.getQuantity());

        winningBids.add(new WinningBid(bidQuantity, bid));
        bid.win(bidQuantity);
        availableQuantity -= bidQuantity;
        revenue = revenue.add(valueOf(bidQuantity).multiply(valueOf(bid.getPrice())));
      } else {
        bid.lost();
      }

      bidRepository.save(bid);
    }

    int totalSoldQuantity = auctionLot.getQuantity() - availableQuantity;
    Instant closingTime = Instant.now();

    auctionLotRepository.close(
        id,
        totalSoldQuantity,
        revenue,
        closingTime);

    return new ClosingSummary(
      unmodifiableList(winningBids),
      totalSoldQuantity,
      revenue,
      closingTime);
  }

  public ClosingSummary getClosingSummary(int id) {
    AuctionLot auctionLot = getAuctionLot(id);

    if (auctionLot
        .getStatus()
        .equals(auctionLot.statusOpened())) {
      throw new BusinessException("Auction is not closed.");
    }

    List<WinningBid> winningBids = bidRepository.getWinningBidsByAuctionLotId(id)
        .stream()
        .map(bid -> new WinningBid(bid.getWinQuantity(), bid))
        .toList();

    return new ClosingSummary(
      winningBids,
      auctionLot.getTotalSoldQuantity(),
      auctionLot.getTotalRevenue(),
      auctionLot.getClosingTime());
  }
}
