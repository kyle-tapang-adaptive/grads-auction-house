package com.weareadaptive.auction.controller.dto.bids;

public class ClosedBidResponse extends BidResponse {
  private final int winQuantity;

  public ClosedBidResponse(
      String username,
      int quantity,
      double price,
      String state,
      int winQuantity) {
    super(username, quantity, price, state);
    this.winQuantity = winQuantity;
  }

  public int getWinQuantity() {
    return winQuantity;
  }
}
