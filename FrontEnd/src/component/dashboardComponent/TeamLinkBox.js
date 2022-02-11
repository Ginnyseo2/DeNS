import React from "react";
import styled from "styled-components";
import { Link, Navigate, Router } from "react-router-dom";

export default function TeamLinkBox(props) {

    return (
        <Box>
            <Link to={{
                pathname:`/afterlogin/team/${props.team_id}`,
                hash: "helloasd",
                search: "helloasd",
                state: {
                    title: props.title,
                    content: "hello",
                }
            }}>
                <Title>{props.title}</Title>
            </Link>
        </Box>
    )
};
const Box = styled.div`
    border-radius: 5px;
    
    width: 20vh;
    height: 20vh;
    position:relative;
    left: 50%;
    transform:translate(-50%);

    display:flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    box-shadow: 3px 3px 13px rgba(244,106,114,1);
`;
const Title = styled.h3`
    font-size: 1em;
`