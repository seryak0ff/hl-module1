import { check } from 'k6';
import http from 'k6/http';
import { Writer, SchemaRegistry } from 'k6/x/kafka';

// Kafka        ^d     ^c ^`   ^f   ^o
const topic = __ENV.KAFKA_TOPIC || 'var17';
const brokers = [__ENV.KAFKA_BROKERS || 'hl22.zil:9094'];
const schemaRegistry = new SchemaRegistry();

const writer = new Writer({
    brokers: brokers,
    topic: topic,
});

//  ^s       ^`   ^f   ^o UUID
function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

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

export const options = {
    vus: 50,
    duration: '30s',
};

function generateUserMessage() {
    return {
        entity: 'USER',
        operation: 'POST',
        payload: {
            id: generateUUID(),
            login: `user_${__VU}_${__ITER}`,
            university: universities[Math.floor(Math.random() * universities.length)],
            subscription_end_date: new Date(Date.now() + 86400000 * Math.floor(Math.random() * 365))
                .toISOString().split('T')[0]
        }
    };
}

export default function () {
    const chance = Math.random();

    if (chance < 0.5) {
        // 5%        ^`   ^a                   ^a ^l    Kafka
        const message = generateUserMessage();
        try {
            writer.produce({
                messages: [{
                    key: schemaRegistry.serialize({
                        data: generateUUID(),
                        schemaType: 'STRING'
                    }),
                    value: schemaRegistry.serialize({
                        data: JSON.stringify(message),
                        schemaType: 'STRING'
                    })
                }],
            });

            check(null, {
                'message sent to Kafka successfully': () => true
            });
        } catch (e) {
            check(null, {
                'message sent to Kafka successfully': () => false
            });
        }
    } else {
        // 95%        ^`   ^a           ^g ^b         HTTP
        const res = http.get('http://10.60.3.21:31702/api/analytics/university-statistics');

        check(res, {
            'download activity retrieved': (r) => r.status === 200,
        });
    }
}
