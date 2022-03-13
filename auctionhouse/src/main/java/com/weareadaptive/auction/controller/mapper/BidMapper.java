package com.weareadaptive.auction.controller.mapper;

import com.weareadaptive.auction.controller.dto.bids.BidResponse;
import com.weareadaptive.auction.controller.dto.bids.ClosedBidResponse;
import com.weareadaptive.auction.controller.dto.bids.OpenBidResponse;
import com.weareadaptive.auction.model.Bid;
import com.weareadaptive.auction.model.WinningBid;

public class BidMapper {
  public static BidResponse map(Bid bid) {
    if (bid.getState()
        .equals(
        bid.statePending()
        )) {
      return new OpenBidResponse(
        bid.getUsername(),
        bid.getQuantity(),
        bid.getPrice(),
        bid.getState()
      );
    }

    return new ClosedBidResponse(
      bid.getUsername(),
      bid.getQuantity(),
      bid.getPrice(),
      bid.getState(),
      bid.getWinQuantity()
    );
  }

  public static BidResponse map(WinningBid winningBid) {
    Bid bid = winningBid.originalBid();
    return new ClosedBidResponse(
      bid.getUsername(),
      bid.getQuantity(),
      bid.getPrice(),
      bid.getState(),
      winningBid.quantity()
    );
  }
}
