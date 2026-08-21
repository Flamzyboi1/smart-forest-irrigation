const API_HEADERS = () => ({'Authorization':'Bearer '+(localStorage.getItem('forestToken')||''),'Content-Type':'application/json'});
function role(){return localStorage.getItem('forestRole')||'';}
function currentUser(){return localStorage.getItem('forestUser')||'';}
function logout(){localStorage.clear();location.href='/login.html';}
function esc(v){return String(v??'').replace(/[&<>'\"]/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;',"'":'&#39;','\"':'&quot;'}[c]));}
async function api(url,options={}){const r=await fetch(url,{...options,headers:{...API_HEADERS(),...(options.headers||{})}});if(r.status===401){localStorage.clear();location.href='/login.html';throw new Error('Unauthorized');}if(!r.ok){let msg='Request failed';try{const d=await r.json();msg=d.error||d.message||msg;}catch{}throw new Error(msg);}if(r.status===204)return null;return r.json();}
function setupNav(active){document.querySelectorAll('[data-nav]').forEach(a=>a.classList.toggle('active',a.dataset.nav===active));const users=document.getElementById('userNav');if(users)users.style.display='block';const name=document.getElementById('currentUserName');if(name)name.textContent=(localStorage.getItem('forestFullName')||currentUser())+' · '+role();}
