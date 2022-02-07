package com.ssafy.BackEnd.repository;


import com.ssafy.BackEnd.entity.ProfileKeyword;
import com.ssafy.BackEnd.entity.TeamFeedKeyword;
import com.ssafy.BackEnd.entity.TeamKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamKeywordRepository extends JpaRepository<TeamKeyword, Long> {

    @Query(value = "select k from TeamKeyword k where teamkeyword_id = :teamkeyword_id and team_id = :team_id") //teamkeyword_id로 수정
    TeamKeyword findTeamKeyword(Long teamkeyword_id, Long team_id);

    @Query(value = "select k from TeamKeyword k where name = :name")
    TeamKeyword findByName(String name);
}
