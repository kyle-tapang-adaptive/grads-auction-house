package com.weareadaptive.auction.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Bid")
public class Bid {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int auctionLotId;
  private String username;
  private int quantity;
  private double price;
  private String state;
  private int winQuantity;

  public Bid() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAuctionLotId() {
    return auctionLotId;
  }

  public void setAuctionLotId(int auctionLotId) {
    this.auctionLotId = auctionLotId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String user) {
    this.username = user;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public int getWinQuantity() {
    return winQuantity;
  }

  public void setWinQuantity(int winQuantity) {
    this.winQuantity = winQuantity;
  }

  public void lost() {
    if (!state.equals(statePending())) {
      throw new BusinessException("Must be a pending bid");
    }

    state = stateLost();
  }

  public void win(int winQuantity) {
    if (!state.equals(statePending())) {
      throw new BusinessException("Must be a pending bid");
    }

    if (quantity < winQuantity) {
      throw new BusinessException("winQuantity must be lower or equal to to the bid quantity");
    }

    state = stateWin();
    this.winQuantity = winQuantity;
  }

  @Override
  public String toString() {
    return "Bid{"
      + "user=" + username
      + ", price=" + price
      + ", quantity=" + quantity
      + '}';
  }

  public String statePending() {
    return "PENDING";
  }

  public String stateWin() {
    return "WIN";
  }

  public String stateLost() {
    return "LOST";
  }
}
