import axios from 'axios';
import React, { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, Outlet } from 'react-router-dom';
import { store } from '../..';
import { apiInstance } from '../../api';
import { getMember, test11 } from '../../api/test';
import { API_BASE_URL } from '../../config';
import { authUser, getTOKEN, loginUser, sessionCheck } from '../../redux/userreduce';
import HeaderBox from './HeaderBox';
import Sidebar from './sidebar';

export default function Back(props) {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const token = useSelector(state => state.user.token);
    const [cookies] = useCookies();

    useEffect(() => {
        //새로고침 시
        if (!token) {
            //쿠키에 있는지 먼저 확인
            if (cookies.token) {
                //다시 리덕스로 넣어주기.
                dispatch(sessionCheck(cookies));
            //쿠키에도 없다면
            } else {
                navigate(`/signin`);
            }
        }
    }, []);

    // console.log(dispatch());

    return (
        <>
        <HeaderBox />
        <Sidebar />
        <div className='tewst' style={{ backgroundColor:'#fde1e36b' }}>
                <Outlet />
        </div>
        </>
        )

}