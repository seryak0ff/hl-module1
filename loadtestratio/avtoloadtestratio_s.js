import http from 'k6/http';
import { check } from 'k6';
import { Rate } from 'k6/metrics';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';

// Метрики для отслеживания
const errorRate = new Rate('errors');

// Генератор UUID для K6
function generateUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    const r = Math.random() * 16 | 0;
    const v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}

// Список университетов
const universities = [
  "Lomonosov Moscow State University",
  "Saint Petersburg State University",
  "Novosibirsk State University",
  "Tomsk State University",
  "Higher School of Economics",
  "Bauman Moscow State Technical University",
  "Moscow Institute of Physics and Technology",
  "Ural Federal University",
  "Kazan Federal University",
  "ITMO University"
];

// Базовые настройки для всех тестов
export const options = {
  scenarios: {
    "cpu_0_5_ratio_5_95": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '0s',
      env: { WRITE_RATIO: '0.05', CPU_LIMIT: '0.5' }
    },
    "cpu_0_5_ratio_50_50": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '70s',
      env: { WRITE_RATIO: '0.50', CPU_LIMIT: '0.5' }
    },
    "cpu_0_5_ratio_95_5": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '140s',
      env: { WRITE_RATIO: '0.95', CPU_LIMIT: '0.5' }
    },
    "cpu_1_0_ratio_5_95": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '210s',
      env: { WRITE_RATIO: '0.05', CPU_LIMIT: '1.0' }
    },
    "cpu_1_0_ratio_50_50": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '280s',
      env: { WRITE_RATIO: '0.50', CPU_LIMIT: '1.0' }
    },
    "cpu_1_0_ratio_95_5": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '350s',
      env: { WRITE_RATIO: '0.95', CPU_LIMIT: '1.0' }
    },
    "cpu_1_5_ratio_5_95": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '420s',
      env: { WRITE_RATIO: '0.05', CPU_LIMIT: '1.5' }
    },
    "cpu_1_5_ratio_50_50": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '490s',
      env: { WRITE_RATIO: '0.50', CPU_LIMIT: '1.5' }
    },
    "cpu_1_5_ratio_95_5": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '560s',
      env: { WRITE_RATIO: '0.95', CPU_LIMIT: '1.5' }
    },
    "cpu_2_0_ratio_5_95": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '630s',
      env: { WRITE_RATIO: '0.05', CPU_LIMIT: '2.0' }
    },
    "cpu_2_0_ratio_50_50": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '700s',
      env: { WRITE_RATIO: '0.50', CPU_LIMIT: '2.0' }
    },
    "cpu_2_0_ratio_95_5": {
      executor: 'constant-vus',
      vus: 150,
      duration: '60s',
      startTime: '770s',
      env: { WRITE_RATIO: '0.95', CPU_LIMIT: '2.0' }
    }
  }
};

export default function () {
  const writeRatio = parseFloat(__ENV.WRITE_RATIO);
  const chance = Math.random();

  if (chance < writeRatio) {
    // POST /users
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
    }) || errorRate.add(1);

    // Добавляем задержку после POST запроса
    sleep(1);

  } else {
    // GET /download-activity
    const res = http.get('http://192.168.1.50:8080/download-activity');

    check(res, {
      'download activity retrieved': (r) => r.status === 200,
    }) || errorRate.add(1);

    // Добавляем меньшую задержку после GET запроса
    sleep(0.1);
  }
}

// Функция для генерации отчета
export function handleSummary(data) {
  return {
    'stdout': textSummary(data, { indent: ' ', enableColors: true }),
    'results.json': JSON.stringify(data),
  };
} 