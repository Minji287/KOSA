import axios from "axios";
import {apiInstance, authApi, authFileApi} from "./config";

const api = async (url, method, data) => {
  return (
    await axios({
      method: method,
      url,
      data,
    }).catch((e) => {
      console.log(e);
    })
  ).data;
};

export { api };

export const signupReq = async (formData) => {
  const response = await apiInstance.post("/signup", formData);
  return response;
};

export const loginReq = async (loginData) => {
  const response = await apiInstance.post("/login", loginData);
  return response;
};

export const headerTestReq = async () => {
    const response = await authApi.get("/");
    return response;
};

export const boardWriteReq = async (writeData) => {
    const response = await authApi.post("/board/save", writeData);
    return response;
};

export const boardDeleteReq = async (boardNo) => {
    const response = await authApi.delete(`/board/delete/${boardNo}`);
    return response;
};

export const boardUpdateReq = async (boardData) => {
    const response = await authApi.put("/board/update", boardData);
    return response;
};


export const commentWriteReq = async (commentData) => {
    const response = await authApi.post("/board/comment", commentData);
    return response;
};

export const commentDeleteReq = async (commentId) => {
    const response = await authApi.delete(`/board/comment/delete/${commentId}`);
    return response;
};



// 마이페이지 회원정보
export const myPageProfileReq = async () => {
    const response = await authApi.get("mypage/myprofile");
    return response;
};

export const changeEmailReq = async (email, domain) => {
    const response = await authApi.post("mypage/changeemail?email="
        + email + "&domain=" + domain);
    return response;
};

export const pwCheckReq = async (pw) => {
    const response = await authApi.post("mypage/passcheck?inputPass="
        + pw);
    return response;
};

export const pwChangeReq = async (pw, checkPw) => {
    const response = await authApi.post("mypage/passchange?changedPass="
        + pw + "&changedPassCheck=" + checkPw);
    return response;
};

export const userOutReq = async () => {
    const response = await authApi.post("mypage/delaccount");
    return response;
};

// 마이페이지 나의펫
export const myPetReq = async () => {
    const response = await authApi.get("mypet");
    return response;
};

export const addMyPetReq = async (formData) => {
    const response = await authFileApi.put("mypet/add", formData);
    return response;
};

export const changeMyPetReq = async (formData) => {
    const response = await authFileApi.put("mypet/change", formData);
    return response;
};

export const deleteMyPetReq = async () => {
    const response = await authApi.delete("mypet/delete");
    return response;
};

// 마이페이지 나의 게시글
export const mywritingsReq = async (page, boardId) => {
    let response;
    if(boardId === 2) {
        response = await authApi.get("mypage/mywritings?page="
            + page + "&boardId=" + boardId);
    } else if(boardId === 1) {
        response = await authApi.get("mypage/mywritings?page="
            + page + "&boardId=" + boardId + "&size=6");
    }

    return response;
};
