package com.christembassy.pune;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FellowshipRepository extends JpaRepository<Fellowship, Long> {
}
