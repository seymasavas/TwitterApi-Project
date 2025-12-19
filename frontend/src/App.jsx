import { useState, useEffect } from 'react'
import { api } from './api'
import './App.css'

function App() {
  const [user, setUser] = useState(null); 
  const [view, setView] = useState('login'); 
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [userName, setUserName] = useState('');
  const [tweets, setTweets] = useState([]);
  const [newTweetDesc, setNewTweetDesc] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await api.login(email, password);
      console.log("Backend'den gelen kullanıcı verisi:", response.data);
      const loggedInUser = response.data;
      const currentUserId = loggedInUser.id || loggedInUser.userId; 

      if (!currentUserId) {
        alert("Hata: Kullanıcı ID'si backend'den gelmedi!");
        return;
      }

      setUser(loggedInUser);
      setView('home');
      fetchTweets(currentUserId);

    } catch (error) {
      console.error("Login Hatası:", error);
      alert('Giriş başarısız! Console ekranına bak.');
    }
  };


  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await api.register(email, password, userName);
      alert('Kayıt başarılı! Şimdi giriş yapabilirsin.');
      setView('login');
    } catch (error) {
      alert('Kayıt sırasında hata oluştu.');
    }
  };


  const fetchTweets = async (userId) => {
    try {
      const response = await api.getUserTweets(userId);
      setTweets(response.data.reverse()); 
    } catch (error) {
      console.error("Tweetler yüklenemedi", error);
    }
  };


const handlePostTweet = async (e) => {
    e.preventDefault();
    if (!newTweetDesc.trim()) return;
    if (!user) {
        alert("Lütfen önce giriş yapın!");
        return;
    }

    const currentUserId = user.id || user.userId;

    if (!currentUserId) {
        console.error("HATA: Kullanıcı ID'si bulunamadı. User objesi:", user);
        alert("Kullanıcı kimliği (ID) eksik, tweet atılamıyor. Console'a bak.");
        return;
    }

    try {
      console.log("Tweet atılıyor... Gönderilen ID:", currentUserId);
      
      await api.createTweet(currentUserId, newTweetDesc);
      
      setNewTweetDesc(''); 
      fetchTweets(currentUserId);
    } catch (error) {
      console.error("Tweet atma hatası:", error);
      alert('Tweet atılamadı. Tekrar giriş yapmayı dene.');
    }
  };

  const handleLogout = () => {
    setUser(null);
    setView('login');
    setTweets([]);
    setEmail('');
    setPassword('');
  };

  return (
    <div className="container">
      {!user && (
        <div className="auth-box">
          <h1>Twitter API</h1>
          {view === 'login' ? (
            <form onSubmit={handleLogin}>
              <h2>Giriş Yap</h2>
              <input type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required />
              <input type="password" placeholder="Şifre" value={password} onChange={e => setPassword(e.target.value)} required />
              <button type="submit">Giriş</button>
              <p onClick={() => setView('register')}>Hesabın yok mu? Kayıt ol</p>
            </form>
          ) : (
            <form onSubmit={handleRegister}>
              <h2>Kayıt Ol</h2>
              <input type="text" placeholder="Kullanıcı Adı" value={userName} onChange={e => setUserName(e.target.value)} required />
              <input type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required />
              <input type="password" placeholder="Şifre" value={password} onChange={e => setPassword(e.target.value)} required />
              <button type="submit">Kayıt Ol</button>
              <p onClick={() => setView('login')}>Zaten hesabın var mı? Giriş yap</p>
            </form>
          )}
        </div>
      )}


      {user && (
        <div className="dashboard">
          <header>
            <h3>Hoşgeldin, {user.userName || user.email}</h3>
            <button onClick={handleLogout} className="logout-btn">Çıkış</button>
          </header>

          <div className="tweet-composer">
            <textarea 
              placeholder="Neler oluyor?" 
              value={newTweetDesc} 
              onChange={e => setNewTweetDesc(e.target.value)}
            />
            <button onClick={handlePostTweet}>Tweetle</button>
          </div>

          <div className="feed">
            {tweets.length === 0 ? <p>Henüz hiç tweet yok.</p> : tweets.map(tweet => (
              <div key={tweet.id} className="tweet-card">
                <div className="tweet-header">
                  <strong>User #{tweet.userId}</strong>
                  <span>• {new Date(tweet.tweetTime).toLocaleDateString()}</span>
                </div>
                <p>{tweet.tweetDesc}</p>
                {<div className="tweet-actions">
                </div>}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}

export default App