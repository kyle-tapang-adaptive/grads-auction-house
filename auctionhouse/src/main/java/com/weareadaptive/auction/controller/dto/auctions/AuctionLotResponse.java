package com.weareadaptive.auction.controller.dto.auctions;

public class AuctionLotResponse {
  private final int id;
  private final String owner;
  private final String symbol;
  private final double minPrice;
  private final int quantity;
  private final String status;

  public AuctionLotResponse(
      int id,
      String owner,
      String symbol,
      double minPrice,
      int quantity,
      String status
  ) {
    this.id = id;
    this.owner = owner;
    this.symbol = symbol;
    this.minPrice = minPrice;
    this.quantity = quantity;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public String getOwner() {
    return owner;
  }

  public String getSymbol() {
    return symbol;
  }

  public double getMinPrice() {
    return minPrice;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getStatus() {
    return status;
  }
}
