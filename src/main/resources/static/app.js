const API_HEADERS = () => ({ Authorization: 'Bearer ' + (localStorage.getItem('forestToken') || ''), 'Content-Type': 'application/json' });
function role() { return localStorage.getItem('forestRole') || ''; }
function currentUser() { return localStorage.getItem('forestUser') || ''; }
function logout() { localStorage.clear(); location.href = '/login.html'; }
function esc(v) { return String(v ?? '').replace(/[&<>'"]/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', "'": '&#39;', '"': '&quot;' }[c])); }
async function api(url, options = {}) {
  const response = await fetch(url, { ...options, headers: { ...API_HEADERS(), ...(options.headers || {}) } });
  if (response.status === 401) { localStorage.clear(); location.href = '/login.html'; throw new Error('Unauthorized'); }
  if (!response.ok) {
    let message = 'Request failed';
    try { const data = await response.json(); message = data.error || data.message || message; } catch (_) {}
    throw new Error(message);
  }
  if (response.status === 204) return null;
  return response.json();
}
function setupNav(active) {
  document.querySelectorAll('[data-nav]').forEach(link => link.classList.toggle('active', link.dataset.nav === active));
  const users = document.getElementById('userNav');
  if (users) users.style.display = 'block';
  const label = document.getElementById('userNav');
  if (label) label.textContent = role() === 'SUPERADMIN' ? 'Users' : 'My Profile';
  const name = document.getElementById('currentUserName');
  if (name) name.textContent = (localStorage.getItem('forestFullName') || currentUser()) + ' · ' + role();
}
