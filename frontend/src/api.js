import axios from 'axios';

const API_URL = 'http://localhost:3000'; // Backend'in çalıştığı port (Varsayılan 8080)

export const api = {
    // Auth
    login: (email, password) => axios.post(`${API_URL}/auth/login`, { email, password }),
    register: (email, password, userName) => axios.post(`${API_URL}/auth/register`, { email, password, userName }),

    // Tweets
    createTweet: (userId, tweetDesc) => axios.post(`${API_URL}/tweet`, { userId, tweetDesc }),
    getUserTweets: (userId) => axios.get(`${API_URL}/tweet/findByUserId/${userId}`),
    likeTweet: (tweetId, userId) => axios.post(`${API_URL}/like`, { tweetId, userId }),
    dislikeTweet: (tweetId, userId) => axios.delete(`${API_URL}/dislike`, { params: { tweetId, userId } }),
    
};