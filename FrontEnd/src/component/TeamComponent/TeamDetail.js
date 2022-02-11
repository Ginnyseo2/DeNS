import React, { useEffect, useState } from 'react';
import { detail, bringTeamMembers } from '../../api/team';
import styled from "styled-components";
import { useParams } from 'react-router-dom';
import TeamFeedContainer from './TeamFeedContainer';
import TeamMemberInfo from './TeamMemberInfo'

export default function TeamDetail(props) {
    const [teamTitle, setTeamTitle] = useState('');
    const [teamContent, setTeamContent] = useState('');
    const [teamMembers, setTeamMembers] = useState('');

    const teamId = useParams().id;
    // 팀 명 및 팀 소개
    useEffect(() => {
        detail(teamId,
            (response) => {
                setTeamTitle(response.data.title);
                setTeamContent(response.data.content);
            },
            (error) => {
                console.log("오류가 됨.", (error));
            });
        },[]);

    // 팀원 정보
    useEffect(() => {
        bringTeamMembers(teamId,
            (response) => {
                setTeamMembers(response.data);
            },
            (error) => {
                console.log("오류가 됨.", (error));
            });
        },[]);
    // 이름 이메일 가져오기

    return (
    <TeamDetailBox>
        {/* 팀 Title */}
        <TheTeamTitle>
            {teamTitle}
        </TheTeamTitle>
        <TeamDetailGrid>
            {/* 팀 피드 */}
            <TeamFeedContainer>
            </TeamFeedContainer>
            {/* 팀 소개 */}
            <TeamInfoContainer>
                <TeamInfoTitle>팀 소개</TeamInfoTitle>
                {/* 팀장/팀원 이미지 */}
                <ImgBox>
                    {teamMembers ? teamMembers.map((el, key) => {
                        return (
                            <TeamMemberInfo name={el.name} email={el.email} key={key}/>
                        )
                    }) : null}
                    {/* <BringTeamMembersImg 
                    src="https://w.namu.la/s/38cf17d29ddeab5a69f6de682176bbd6b8f71285f5adc1d5465c910f8d7651e8f82db2bdba9e25f1d29affdedb9ddc04edeadc4e7f539ce975eaad093a2b8c68adc73e19b58ff0f4cce679d2f2bb15e273bdcaa5e3db26bd4464e1707b67c69a"
                    ></BringTeamMembersImg>
                    <BringTeamMembersImg 
                    src="https://w.namu.la/s/38cf17d29ddeab5a69f6de682176bbd6b8f71285f5adc1d5465c910f8d7651e8f82db2bdba9e25f1d29affdedb9ddc04edeadc4e7f539ce975eaad093a2b8c68adc73e19b58ff0f4cce679d2f2bb15e273bdcaa5e3db26bd4464e1707b67c69a"
                    ></BringTeamMembersImg>
                    <BringTeamMembersImg 
                    src="https://w.namu.la/s/38cf17d29ddeab5a69f6de682176bbd6b8f71285f5adc1d5465c910f8d7651e8f82db2bdba9e25f1d29affdedb9ddc04edeadc4e7f539ce975eaad093a2b8c68adc73e19b58ff0f4cce679d2f2bb15e273bdcaa5e3db26bd4464e1707b67c69a"
                    ></BringTeamMembersImg>
                    <BringTeamMembersImg 
                    src="https://w.namu.la/s/38cf17d29ddeab5a69f6de682176bbd6b8f71285f5adc1d5465c910f8d7651e8f82db2bdba9e25f1d29affdedb9ddc04edeadc4e7f539ce975eaad093a2b8c68adc73e19b58ff0f4cce679d2f2bb15e273bdcaa5e3db26bd4464e1707b67c69a"
                    ></BringTeamMembersImg> */}
                </ImgBox>
                {/* 팀 Content */}
                <TeamInfoTextBox>
                    <TextBox>
                        {teamContent}
                    </TextBox>
                </TeamInfoTextBox>
                {/* 팀 소개 해쉬태그 */}
                <TeamInfoHashtag>
                    #자바스크립트 #프론트엔드 #자바
                </TeamInfoHashtag>
            </TeamInfoContainer>
        </TeamDetailGrid>
    </TeamDetailBox>
    )
}
const TeamDetailBox = styled.div`
`
const TheTeamTitle = styled.h3`
    position: relative;
    margin-top: 2%;
    left: 5%;
`
const TeamDetailGrid = styled.div`
    position: absolute;
    top: 50%;
    left: 50%;
    transform:translate(-50%, -50%);
    display: grid;
    grid-template-columns: 45vw 20vw;
    // grid-template-rows: 70vh 40vh;
    grid-gap: 14%;
`

// 팀 소개
const TeamInfoContainer = styled.div`
    border: 1px solid;
    // text-align: center;
    border-radius: 5px;

    display: flex;
    flex-direction: column;
    align-items: start;
`
const TeamInfoTitle = styled.h3`
    position: relative;
    margin-top: 5%;
    left: 5%;
    font-weight: bold;
`
const BringTeamMembersImg = styled.img`
    width: 50px;
    height: 50px;
`
const ImgBox = styled.div`
    position: relative;
    left: 5%;
    margin-top: 5%;
    width:90%;
`
const TeamInfoTextBox = styled.div`
    position: relative;
    margin-top: 5%;
    left: 5%;
    width: 90%;
    word-break:break-all;
`
const TextBox = styled.div`
`
const TeamInfoHashtag = styled.div`
    position: relative;
    margin-top: 5%;
    left: 5%;
    width: 90%;
    word-break:break-all;
`