package com.weareadaptive.auction.service;

import static java.lang.String.format;

import com.weareadaptive.auction.exception.KeyDoesNotExistException;
import com.weareadaptive.auction.model.AuctionLot;
import com.weareadaptive.auction.model.AuctionState;
import com.weareadaptive.auction.model.UserState;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class AuctionLotService {
  public static final String AUCTION_LOT_ENTITY = "AuctionLot";
  private final AuctionState auctionState;
  private final UserState userState;

  public AuctionLotService(AuctionState auctionState, UserState userState) {
    this.auctionState = auctionState;
    this.userState = userState;
  }

  public AuctionLot create(String username, String symbol, double minPrice, int quantity) {
    AuctionLot auctionLot = new AuctionLot(
        auctionState.nextId(),
        userState.getByUsername(username)
          .orElseThrow(() -> new KeyDoesNotExistException("User does not exist")),
        symbol,
        quantity,
        minPrice);
    auctionState.add(auctionLot);
    return auctionLot;
  }

  public AuctionLot get(int id) {
    return auctionState.get(id);
  }

  public Stream<AuctionLot> all() {
    return auctionState.stream();
  }

  //private String getUser() {
  //  UserDetails principal = (UserDetails) SecurityContextHolder
  //      .getContext()
  //      .getAuthentication()
  //      .getPrincipal();
  //  return principal.getUsername();
  //}
}
