package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.dto.TeamDto;
import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.repository.*;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

import com.ssafy.BackEnd.entity.Team;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    private final TeamKeywordRepository teamKeywordRepository;


    private HashTagAlgorithm hashTagAlgorithm = new HashTagAlgorithm();

    @Override
    public Team findByTeam(Long team_id) throws NotFoundException {
        Team findTeam = teamRepository.findByTeam(team_id);
        if(findTeam == null) throw new NotFoundException("팀을 찾을 수 없습니다");

        return findTeam;
    }

    @Override
    public Team createTeam(Team team) {
        List<String> keywords = hashTagAlgorithm.strList(team.getContent());

        List<TeamKeyword> teamKeywordList = new ArrayList<>();
        for (String keyword : keywords) {
            if (teamKeywordRepository.findByName(keyword) == null) {
                TeamKeyword newTeamKeyword = new TeamKeyword();
                //newTeamKeyword.setKeyword(newKeyword);
                newTeamKeyword.setCount(1);
                newTeamKeyword.setTeam(team);
                newTeamKeyword.setName(keyword);
                teamKeywordRepository.save(newTeamKeyword);
                teamKeywordList.add(newTeamKeyword);
            } else {
                TeamKeyword findTeamKeyword = teamKeywordRepository.findTeamKeyword(keyword, team);
//                keywordRepository.save(findKeyword);
                findTeamKeyword.setCount(findTeamKeyword.getCount() + 1);

                if (findTeamKeyword == null) {
                    TeamKeyword newTeamKeyword = new TeamKeyword();
                    //newTeamKeyword.setKeyword(findKeyword);
                    newTeamKeyword.setTeam(team);
                    newTeamKeyword.setCount(1);
                    newTeamKeyword.setName(keyword);
                    teamKeywordRepository.save(newTeamKeyword);
                    teamKeywordList.add(newTeamKeyword);
                }
//                else {
////                    TeamKeyword findTeamKeyword = teamKeywordRepository.findTeamKeyword(findKeyword.getTeamkeyword_id(), team.getTeam_id());
//                    findTeamKeyword.setCount(findTeamKeyword.getCount()+1);
//                }
            }
        }
        team.setTeam_keyword(teamKeywordList);

        teamRepository.save(team);
        return team;
    }

    @Override
    public Team modifyTeam(Team team, TeamDto teamDto) {
        team.setTitle(teamDto.getTitle());
        team.setContent(teamDto.getContent());

        List<TeamKeyword> teamKeywords = team.getTeam_keyword(); //기존 팀소개 키워드
        List<String> keywords = hashTagAlgorithm.strList(team.getContent()); //새로운 팀소개 키워드 추출
        List<TeamKeyword> deleteKeywords = new ArrayList<>();
        for (TeamKeyword teamKeyword : teamKeywords) {
            if (keywords.contains(teamKeyword.getName()) == false) { //기존 키워드 안 가지고 있으면 수 감소
                System.out.println(teamKeyword.getName());
                System.out.println("==============if");
                teamKeyword.setCount(teamKeyword.getCount() - 1);
                deleteKeywords.add(teamKeyword);
//                keywords.remove(teamFeedKeyword.getKeyword().getName());
//                teamFeedKeywords.remove(teamFeedKeyword);
            } else if (keywords.contains(teamKeyword.getName())) { //기존키워드에 새로운 키워드가 있으면
                System.out.println(teamKeyword.getName());
                System.out.println("=============else");
                keywords.remove(teamKeyword.getName());
            }
        }
        for (TeamKeyword deleteKeyword : deleteKeywords) {
//            teamFeedKeywords.remove(deleteKeyword);
            if (deleteKeyword.getCount() <= 0) {

//                deleteKeyword.setTeam_feed(null);
//                deleteKeyword.setKeyword(null);
//                teamFeedKeywordRepository.deleteById(deleteKeyword.getTeamfeedkeyword_id());
//                System.out.println(deleteKeyword.getKeyword().getKeyword_id());
                System.out.println("=======remove");
                teamKeywords.remove(deleteKeyword);
                System.out.println("==========delete");
                teamKeywordRepository.delete(deleteKeyword);
//                teamFeedKeywordRepository.deleteById(deleteKeyword.getTeamfeedkeyword_id());
//                keywordRepository.deleteById(deleteKeyword.getKeyword().getKeyword_id());

//                teamFeedKeywords.remove(deleteKeyword);
            }
        }
        for (String key : keywords) System.out.println(key.getBytes(StandardCharsets.UTF_8));
        for (String keyword : keywords) {
            if (teamKeywordRepository.findTeamKeyword(keyword, team) == null) {
                TeamKeyword newTeamKeyword = new TeamKeyword();
                //newTeamKeyword.setKeyword(newKeyword);
                newTeamKeyword.setTeam(team);
                newTeamKeyword.setCount(1);
                teamKeywordRepository.save(newTeamKeyword);
                teamKeywords.add(newTeamKeyword);
            } else {
                TeamKeyword teamKeyword = teamKeywordRepository.findTeamKeyword(keyword, team);
                teamKeyword.setCount(teamKeyword.getCount()+1);
            }
            team.setTeam_keyword(teamKeywords);
            teamRepository.save(team);
        }
        return team;

    }

    @Override
    public void deleteTeam(long team_id) {
        Team team = teamRepository.findByTeam(team_id);
        List<TeamKeyword> teamKeywordList = team.getTeam_keyword();
        for (TeamKeyword teamKeyword : teamKeywordList) {
//            teamFeedKeyword.getKeyword().setCount(teamFeedKeyword.getKeyword().getCount() - 1);
            teamKeywordRepository.delete(teamKeyword);
            System.out.println("=======================");
//            teamFeedKeywords.remove(teamFeedKeyword);
//            System.out.println(teamFeedKeyword.getKeyword().getName());
        }
        teamRepository.delete(team);
        System.out.println("=====remove");
    }

    @Override
    public List<Team> showFindTeamList(String keyword) {
        List<Team> teams = teamRepository.findByTitleContaining(keyword);
        System.out.println(teams.toString());
        return teams;
    }

    @Override
    public List<Team> showTeamList() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(team -> teams.add(team));

        return teams;
    }

    @Override
    public List<Team> showMyTeamList(Long profile_id) {
        List<Team> my_teams = new ArrayList<>();
        teamRepository.showMyTeamList(profile_id).forEach(myteam -> my_teams.add(myteam));

        System.out.println("DFSDF");
        System.out.println(my_teams.size());

        for (Team team : my_teams) {
          System.out.println("팀제목" + team.getTitle());
        }

        return my_teams;

    }

    @Override
    public void modifyTeamProfile(long team_id, Team team) {
        Team old_team = teamRepository.findByTeam(team_id);
        old_team.setContent(team.getContent()); //content(팀 프로필)만 수정

        teamRepository.save(team);
    }

    @Override
    public TeamMember setTeamLeader(Team team, String email) {
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(team);
        teamMember.setUser(userRepository.findByEmail(email));
        teamMember.setTeam_identity(TeamMemberIdentity.LEADER);

        teamMemberRepository.save(teamMember);

        return teamMember;
    }

    @Override
    public List<Team> findTeamByKeyword(String keyword) {
        List<TeamKeyword> findTeamKeywords = teamKeywordRepository.findByNameContaining(keyword);
        List<Team> findTeams = new ArrayList<>();
        for (TeamKeyword teamKeyword : findTeamKeywords) {
            System.out.println(teamKeyword.getName());
            if (!findTeams.contains(teamKeyword.getTeam())) {
                findTeams.add(teamKeyword.getTeam());
            }
        }
        return findTeams;
    }
}

