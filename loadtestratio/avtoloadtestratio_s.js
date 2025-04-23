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

// Конфигурации тестов
const testConfigs = [
  { cpu: 0.5, writeRatio: 0.05, name: "0.5 CPU, 5/95" },
  { cpu: 0.5, writeRatio: 0.50, name: "0.5 CPU, 50/50" },
  { cpu: 0.5, writeRatio: 0.95, name: "0.5 CPU, 95/5" },
  { cpu: 1.0, writeRatio: 0.05, name: "1.0 CPU, 5/95" },
  { cpu: 1.0, writeRatio: 0.50, name: "1.0 CPU, 50/50" },
  { cpu: 1.0, writeRatio: 0.95, name: "1.0 CPU, 95/5" },
  { cpu: 1.5, writeRatio: 0.05, name: "1.5 CPU, 5/95" },
  { cpu: 1.5, writeRatio: 0.50, name: "1.5 CPU, 50/50" },
  { cpu: 1.5, writeRatio: 0.95, name: "1.5 CPU, 95/5" },
  { cpu: 2.0, writeRatio: 0.05, name: "2.0 CPU, 5/95" },
  { cpu: 2.0, writeRatio: 0.50, name: "2.0 CPU, 50/50" },
  { cpu: 2.0, writeRatio: 0.95, name: "2.0 CPU, 95/5" }
];

// Базовые настройки для всех тестов
export const options = {
  scenarios: testConfigs.map(config => ({
    name: config.name,
    exec: 'default',
    vus: 150,
    duration: '60s',
    env: {
      WRITE_RATIO: config.writeRatio.toString(),
      CPU_LIMIT: config.cpu.toString()
    }
  }))
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

  } else {
    // GET /download-activity
    const res = http.get('http://192.168.1.50:8080/download-activity');

    check(res, {
      'download activity retrieved': (r) => r.status === 200,
    }) || errorRate.add(1);
  }
}

// Функция для генерации отчета
export function handleSummary(data) {
  return {
    'stdout': textSummary(data, { indent: ' ', enableColors: true }),
    'results.json': JSON.stringify(data),
  };
} 