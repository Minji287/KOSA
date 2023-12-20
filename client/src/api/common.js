import { instance, authInstance, fileInstance } from "./config";

export const signupReq = async (signupDto) => {
  return await instance.post("/auth/signup", signupDto);
};

export const loginReq = async (loginDto) => {
  return await instance.post("/auth/login", loginDto);
};

export const emailAuthReq = async (emailDto) => {
  return await instance.post("/auth/send-email", emailDto);
};

export const nickDupCheckReq = async (nick) => {
  return await instance.get("/auth/chk-nickname?nickname=" + nick);
};

export const emailDupCheckReq = async (email) => {
  return await instance.get("/auth/chk-email?email=" + email);
};

export const emailAuthResultReq = async (key, task) => {
  return await instance.get("/auth/verify-email?link=" + key + "&task=" + task);
};

export const oauthTokenReq = async () => {
  return await instance.post("/auth/oauth2/access-token");
};

export const getUserInfoReq = async () => {
  return await authInstance.get("/auth/user");
};

// comment

export const getCommentList = async (articleId) => {
  return await instance.get("/comment/" + articleId);
};

export const createUserComment = async (commentRequestDTO, articleId) => {
  return await authInstance.post("/comment/" + articleId, commentRequestDTO);
};

export const createGuestComment = async (commentRequestDTO, articleId) => {
  return await instance.post("/comment/guest/" + articleId, commentRequestDTO);
};

export const commentPasswordConfirm = async (commentRequestDTO, commentId) => {
  return await instance.post(
    "/comment/guest/password-confirm/" + commentId,
    commentRequestDTO
  );
};

export const editUserComment = async (
  commentRequestDTO,
  articleId,
  commentId
) => {
  return await authInstance.patch(
    "/comment/" + articleId + "/" + commentId,
    commentRequestDTO
  );
};

export const editGuestComment = async (
  commentRequestDTO,
  articleId,
  commentId
) => {
  return await instance.patch(
    "/comment/guest/" + articleId + "/" + commentId,
    commentRequestDTO
  );
};

export const deleteUserComment = async (articleId, commentId) => {
  return await authInstance.delete("/comment/" + articleId + "/" + commentId);
};

export const deleteGuestComment = async (articleId, commentId) => {
  return await instance.delete("/comment/guest/" + articleId + "/" + commentId);
};

export const postArticleRequest = async (requestArticleDto) => {
  return await authInstance.post("/article", requestArticleDto);
};

export const patchArticleRequest = async (articleId, requestArticleDto) => {
  return await authInstance.patch("/article/"+ articleId, requestArticleDto)
}

export const deleteArticleRequest = async (articleId) => {
  return await authInstance.delete("/article/" + articleId);
};

export const getUser = async (userId) => {
  return await instance.get("/users/"+ userId);
}
export const listMyPost = async (userId,page) => {
  return await instance.get("/users/"+userId+"/posts?page="+ page);
}
export const editNickname = async (userId,nickname) => {
  return await authInstance.patch("/users/"+userId+"/updateNickname", nickname);
}
export const handleWithDrawal = async (userId,nickname) => {
  return await instance.patch("/users/"+userId+"/inactive", nickname);
}
export const changeProfileImage = async (userId,formData) => {
  return await fileInstance.post("/users/"+userId+"/uploadProfileImg", formData);
}
export const listMyComment = async (userId,page) => {
  return await instance.get("/users/"+userId+"/comments?page="+page);
}
export const listSubscribers = async (userId) => {
  return await instance.get("/users/"+userId+"/subscriber");
}
export const listsubscribedWriter = async (userId) => {
  return await instance.get("/users/"+userId+"/subscribedWriter");
}
export const subscribeUser = async (userId) => {
  return await authInstance.get("/users/"+userId+"/subscribe");
}
export const getUserByEmail = async (email) => {
  return await instance.get("/users/"+email);
}