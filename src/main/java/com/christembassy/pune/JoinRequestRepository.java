package com.christembassy.pune;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<FellowshipJoinRequest, Long> {
    List<FellowshipJoinRequest> findByFellowshipId(Long fellowshipId);
    List<FellowshipJoinRequest> findByStatus(String status);
}
