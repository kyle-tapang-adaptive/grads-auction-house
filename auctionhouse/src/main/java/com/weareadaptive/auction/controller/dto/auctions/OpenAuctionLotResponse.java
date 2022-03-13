package com.weareadaptive.auction.controller.dto.auctions;

public class OpenAuctionLotResponse extends AuctionLotResponse {
  public OpenAuctionLotResponse(
      int id,
      String owner,
      String symbol,
      double minPrice,
      int quantity,
      String status) {
    super(id, owner, symbol, minPrice, quantity, status);
  }
}
