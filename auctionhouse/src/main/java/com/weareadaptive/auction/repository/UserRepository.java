package com.weareadaptive.auction.repository;

import com.weareadaptive.auction.model.User;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("select u from AuctionUser u where u.username=?1 and u.password=?2")
  Optional<User> validateUsernamePassword(String username, String password);

  @Query("select u from AuctionUser u where u.id=?1")
  User get(int id);

  @Query("select u from AuctionUser u where u.username=?1")
  Optional<User> getByUsername(String user);

  @Query("select u from AuctionUser u")
  Stream<User> all();
}
