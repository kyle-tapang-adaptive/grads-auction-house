package com.weareadaptive.auction.controller;


import static com.weareadaptive.auction.controller.mapper.AuctionLotMapper.map;

import com.weareadaptive.auction.controller.dto.AuctionLotResponse;
import com.weareadaptive.auction.controller.dto.CreateAuctionLotRequest;
import com.weareadaptive.auction.controller.dto.UserResponse;
import com.weareadaptive.auction.controller.mapper.AuctionLotMapper;
import com.weareadaptive.auction.model.AuctionLot;
import com.weareadaptive.auction.model.Bid;
import com.weareadaptive.auction.service.AuctionLotService;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
@PreAuthorize("hasRole('ROLE_USER')")
public class AuctionLotController {
  private final AuctionLotService auctionLotService;

  public AuctionLotController(AuctionLotService auctionLotService) {
    this.auctionLotService = auctionLotService;
  }

  @PostMapping("/auctions")
  @ResponseStatus(HttpStatus.CREATED)
  AuctionLotResponse create(Principal principal,
                            @RequestBody @Valid CreateAuctionLotRequest createAuctionLotRequest) {
    AuctionLot auctionLot = auctionLotService.create(
        principal.getName(),
        createAuctionLotRequest.symbol(),
        createAuctionLotRequest.minPrice(),
        createAuctionLotRequest.quantity());
    return map(auctionLot);
  }

  @GetMapping("/auctions/{id}")
  AuctionLotResponse oneAuction(@PathVariable int id) {
    AuctionLot auctionLot = auctionLotService.get(id);
    return map(auctionLot);
  }

  @GetMapping("/auctions/")
  List<AuctionLotResponse> allAuctions(Principal principal) {
    return auctionLotService.all()
      .map(AuctionLotMapper::map)
      .toList();
  }
}
