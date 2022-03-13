package com.weareadaptive.auction.controller.dto.bids;

public class OpenBidResponse extends BidResponse {
  public OpenBidResponse(String username, int quantity, double price, String state) {
    super(username, quantity, price, state);
  }
}
