package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.Repository;

import com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query(value = "SELECT * FROM match_game WHERE active = true LIMIT 1", nativeQuery = true)
    Match findActiveMatch();


}
