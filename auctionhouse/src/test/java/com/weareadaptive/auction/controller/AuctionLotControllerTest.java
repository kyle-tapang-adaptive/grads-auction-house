package com.weareadaptive.auction.controller;

import com.github.javafaker.Faker;
import com.weareadaptive.auction.TestData;
import com.weareadaptive.auction.controller.dto.CreateAuctionLotRequest;
import com.weareadaptive.auction.model.AuctionLot;
import com.weareadaptive.auction.model.User;
import com.weareadaptive.auction.service.AuctionLotService;
import com.weareadaptive.auction.service.UserService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static com.weareadaptive.auction.TestData.ADMIN_AUTH_TOKEN;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuctionLotControllerTest {

  @Autowired
  private AuctionLotService auctionLotService;
  @Autowired
  private UserService userService;
  @Autowired
  private TestData testData;
  @LocalServerPort
  private int port;
  private String uri;
  private final Faker faker = new Faker();

  @BeforeEach
  public void initialiseRestAssuredMockMvcStandalone() {
    uri = "http://localhost:" + port;
  }

  @DisplayName("create should create and return the new auction")
  @Test
  public void shouldReturnAuctionIfCreated() {
    User testUser = testData.user1();
    var createRequest =
      new CreateAuctionLotRequest(
        "APPL",
        50.0F,
        5
      );

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.getToken(testUser))
      .contentType(ContentType.JSON)
      .body(createRequest)
      .when()
      .post("/auctions")
      .then()
      .statusCode(HttpStatus.CREATED.value())
      .body("id", greaterThanOrEqualTo(0))
      .body("owner", equalTo(testUser.getUsername()))
      .body("symbol", equalTo(createRequest.symbol()))
      .body("minPrice", equalTo((float) createRequest.minPrice()))
      .body("quantity", equalTo(createRequest.quantity()))
      .body("status", equalTo(AuctionLot.Status.OPENED.toString()));
    //@formatter:on
  }

  @DisplayName("get should return an auctionLot")
  @Test
  public void shouldReturnUserIfAuctionLot() {
    User testUser = testData.user1();
    var createRequest =
      new CreateAuctionLotRequest(
        "APPL",
        50.0F,
        5
      );

    //@formatter:off
    given()
      .baseUri(uri)
      .header(AUTHORIZATION, testData.getToken(testUser))
      .pathParam("id", 0)
      .when()
      .get("/auctions/{id}")
      .then()
      .statusCode(HttpStatus.OK.value())
      .body("id", greaterThanOrEqualTo(0))
      .body("owner", equalTo(testUser.getUsername()))
      .body("symbol", equalTo(createRequest.symbol()))
      .body("minPrice", equalTo((float) createRequest.minPrice()))
      .body("quantity", equalTo(createRequest.quantity()))
      .body("status", equalTo(AuctionLot.Status.OPENED.toString()));
    //@formatter:on
  }
}
