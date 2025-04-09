import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 50 },   // Плавный рост нагрузки до 50 VU за 30 сек
    { duration: '1m', target: 100 },   // Удерживаем 100 VU 1 минуту
    { duration: '20s', target: 0 },    // Плавное снижение нагрузки
  ],
  thresholds: {
    http_req_duration: ['p(95)<500'], // 95% запросов должны быть быстрее 500ms
  },
};

export default function () {
  const url = 'http://10.82.165.165:8080/api/users'; // URL
  
  const res = http.get(url, {
    headers: { 'Accept': 'application/json' },
  });

  check(res, {
    'status is 200': (r) => r.status === 200,
  });

  sleep(1); // Пауза между запросами 1 секунда
}
