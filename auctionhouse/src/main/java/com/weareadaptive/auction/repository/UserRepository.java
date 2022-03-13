package com.weareadaptive.auction.repository;

import com.weareadaptive.auction.model.User;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("select u from AuctionUser u where u.username=?1 and u.password=?2")
  Optional<User> validateUsernamePassword(String username, String password);

  @Query("select u from AuctionUser u where u.id=?1")
  Optional<User> get(int id);

  @Query("select u from AuctionUser u where u.username=?1")
  Optional<User> getByUsername(String username);

  @Query("select u from AuctionUser u")
  Collection<User> all();

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("update AuctionUser u "
        + "set u.firstName = :firstName, "
        +     "u.lastName = :lastName, "
        +     "u.organisation = :organisation "
        + "where u.id = :id")
  void update(
      @Param("id") int id,
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("organisation") String organisation);

  @Modifying
  @Transactional
  @Query("update AuctionUser u set u.blocked = true where u.id=?1")
  void block(int id);

  @Modifying
  @Transactional
  @Query("update AuctionUser u set u.blocked = false where u.id=?1")
  void unblock(int id);
}
