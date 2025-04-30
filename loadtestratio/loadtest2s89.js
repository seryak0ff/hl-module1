import http from 'k6/http';
import { check } from 'k6';

//  ^s       ^`   ^b   ^` UUID      ^o K6 (             Python uuid)
function generateUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    const r = Math.random() * 16 | 0;
    const v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}

export const options = {
  vus: 50,            //          ^g   ^a ^b                ^`           ^k ^e        ^l         ^b
  duration: '30s',     //        ^b     ^l     ^a ^b ^l  ^b   ^a ^b
};

const universities = ["Lomonosov Moscow State University",
                      "Saint Petersburg State University",
                      "Novosibirsk State University",
                      "Tomsk State University",
                      "Higher School of Economics",
                      "Bauman Moscow State Technical University",
                      "Moscow Institute of Physics and Technology",
                      "Ural Federal University",
                      "Kazan Federal University",
                      "ITMO University"];


export default function () {
  const chance = Math.random();

  if (chance < 0.05) {
    // 5%  ^`^t POST /users
    const payload = JSON.stringify({
      id: generateUUID(),
      login: `user_${__VU}_${__ITER}`,
      university: universities[Math.floor(Math.random() * universities.length)],
      subscription_end_date: new Date(Date.now() + 86400000 * Math.floor(Math.random() * 365))
        .toISOString().split('T')[0],
    });

    const headers = { 'Content-Type': 'application/json' };

    const res = http.post('http://10.60.3.21:8080/users', payload, { headers });

    check(res, {
      'user created': (r) => r.status === 200 || r.status === 201,
    });

  } else {
    // 95%  ^`^t GET /download-activity
    const res = http.get('http://10.60.3.21:8081/api/analytics/university-statistics');

    check(res, {
      'download activity retrieved': (r) => r.status === 200,
    });
  }
}