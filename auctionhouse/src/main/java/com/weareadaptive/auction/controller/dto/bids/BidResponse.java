package com.weareadaptive.auction.controller.dto.bids;

public class BidResponse {
  private final String username;
  private final int quantity;
  private final double price;
  private final String state;

  public BidResponse(String username, int quantity, double price, String state) {
    this.username = username;
    this.quantity = quantity;
    this.price = price;
    this.state = state;
  }

  public String getUsername() {
    return username;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getPrice() {
    return price;
  }

  public String getState() {
    return state;
  }
}
