import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend, Rate } from 'k6/metrics';

// Конфигурация теста
const BASE_URL = 'http://10.82.165.165:8080';
const ENDPOINTS = [
  '/users',
  '/articles',
  '/downloads',
  '/users/3fa85f64-5717-4562-b3fc-2c963f66afa6'
];

// Метрики
const responseTime = new Trend('response_time');
const errorRate = new Rate('errors');

// Параметры нагрузки по умолчанию
const defaultStages = [
  { duration: '30s', target: 20 },  // Плавный рост
  { duration: '1m', target: 50 },   // Пиковая нагрузка
  { duration: '30s', target: 0 }    // Завершение
];

export const options = {
  stages: JSON.parse(__ENV.STAGES || JSON.stringify(defaultStages)),
  thresholds: {
    'errors': ['rate<0.05'],
    'http_req_duration{endpoint:/users}': ['p(95)<500'],
    'http_req_duration{endpoint:/articles}': ['p(95)<300'],
    'http_req_duration{endpoint:/downloads}': ['p(95)<700']
  },
  rps: parseInt(__ENV.RPS || 10)  // Контроль RPS
};

// Генератор тестовых данных
function generateUsers() {
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
  
  // Генерация уникального логина без шаблонных строк
  const uniqueLogin = 'user_' + __VU + '_' + __ITER + '_' + new Date().getTime();
  
  return {
    id: crypto.randomUUID ? crypto.randomUUID() : generateUUID(), // Фоллбек для старых версий
    login: uniqueLogin,
    university: universities[Math.floor(Math.random() * universities.length)],
    subscription_end_date: new Date(Date.now() + 86400000 * Math.floor(Math.random() * 365))
      .toISOString().split('T')[0]
  };
}

// Фоллбек-генератор UUID для старых версий K6
function generateUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    const r = Math.random() * 16 | 0;
    const v = c == 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}

export default function () {
  const endpoint = ENDPOINTS[Math.floor(Math.random() * ENDPOINTS.length)];
  let res;
  let payload;
  let params = { tags: { endpoint } };

  // Логика запросов
  switch(endpoint) {
    case '/users':
      if (Math.random() > 0.7) {
        payload = JSON.stringify(generateUsers());
        params.headers = { 'Content-Type': 'application/json' };
        res = http.post(`${BASE_URL}${endpoint}`, payload, params);
      } else {
        res = http.get(`${BASE_URL}${endpoint}`, params);
      }
      break;
      
    default:
      res = http.get(`${BASE_URL}${endpoint}`, params);
  }

  // Проверка результатов
  const isOK = check(res, {
    'Status 2xx': (r) => r.status >= 200 && r.status < 300,
    'Response time OK': (r) => r.timings.duration < 1000
  }) || false;

  if (!isOK) errorRate.add(1);
  
  responseTime.add(res.timings.duration);
  sleep(1 / (__ENV.RPS || 10));  // Регулировка интервала между запросами
}

