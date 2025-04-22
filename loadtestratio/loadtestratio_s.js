import http from 'k6/http';
import { check } from 'k6';

// Генератор UUID для K6 (аналог Python uuid)
function generateUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    const r = Math.random() * 16 | 0;
    const v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}

export const options = {
  vus: 150,            // количество одновременных пользователей
  duration: '60s',     // длительность теста
};

const universities = ['MIT', 'Stanford', 'Harvard', 'Oxford', 'Cambridge'];

export default function () {
  const chance = Math.random();

  if (chance < 0.05) {
    // 5% — POST /users
    const payload = JSON.stringify({
      id: generateUUID(),
      login: `user_${__VU}_${__ITER}`,
      university: universities[Math.floor(Math.random() * universities.length)],
      subscription_end_date: new Date(Date.now() + 86400000 * Math.floor(Math.random() * 365))
        .toISOString().split('T')[0],
    });

    const headers = { 'Content-Type': 'application/json' };

    const res = http.post('http://192.168.1.50:8080/users', payload, { headers });

    check(res, {
      'user created': (r) => r.status === 200 || r.status === 201,
    });

  } else {
    // 95% — GET /download-activity
    const res = http.get('http://192.168.1.50:8080/download-activity');

    check(res, {
      'download activity retrieved': (r) => r.status === 200,
    });
  }
}
