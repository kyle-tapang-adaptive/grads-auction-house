package com.weareadaptive.auction.model;

import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "AuctionLot")
public class AuctionLot {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String owner;
  private String symbol;
  private double minPrice;
  private int quantity;
  private String status;
  private int totalSoldQuantity;
  private BigDecimal totalRevenue;
  private Instant closingTime;

  public AuctionLot() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public double getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(double minPrice) {
    this.minPrice = minPrice;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getTotalSoldQuantity() {
    return totalSoldQuantity;
  }

  public void setTotalSoldQuantity(int totalSoldQuantity) {
    this.totalSoldQuantity = totalSoldQuantity;
  }

  public BigDecimal getTotalRevenue() {
    return totalRevenue;
  }

  public void setTotalRevenue(BigDecimal totalRevenue) {
    this.totalRevenue = totalRevenue;
  }

  public Instant getClosingTime() {
    return closingTime;
  }

  public void setClosingTime(Instant closingTime) {
    this.closingTime = closingTime;
  }

  @Override
  public String toString() {
    return "AuctionLot{"
        + "owner=" + owner
        + ", title='" + symbol + '\''
        + ", status=" + status
        + '}';
  }

  public String statusOpened() {
    return "OPENED";
  }

  public String statusClosed() {
    return "CLOSED";
  }
}
