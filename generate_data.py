#!/usr/bin/env python3
import argparse
import requests
from faker import Faker
import random
import sys
from concurrent.futures import ThreadPoolExecutor
from datetime import datetime, timedelta
import time
import uuid
import json

class DataGenerator:
    def __init__(self, base_url):
        self.fake = Faker()
        self.base_url = base_url.rstrip('/')

    def clear_data(self, endpoint):
        """Очистка данных через эндпоинт /clear"""
        try:
            response = requests.delete(f"{self.base_url}/{endpoint}/clear")
            response.raise_for_status()
            print(f"Очищены данные для {endpoint}")
        except Exception as e:
            print(f"Ошибка очистки {endpoint}: {str(e)}")
            sys.exit(1)

    def generate_user(self):
        """Генерация тестового пользователя"""
        return {
            "id": str(uuid.uuid4()),
            "login": self.fake.unique.user_name(),
            "university": random.choice([
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
            ]),
            "subscription_end_date": self.fake.date_between(start_date='today', end_date='+1y').isoformat()
        }

    def generate_article(self):
        """Генерация тестовой статьи"""
        return {
            "id": str(uuid.uuid4()),
            "doi": f"10.{self.fake.random_int(1000,9999)}/{self.fake.unique.uuid4()[:8]}",
            "title": self.fake.sentence(nb_words=6),
            "author": self.fake.name(),
            "publication_year": self.fake.year()  # Обратите внимание, это поле будет преобразовано в publicationYear
        }

    def generate_download(self, user_id, article_id):
        """Генерация тестовой загрузки с новой структурой (только ID пользователя и статьи)"""
        # Задаем временной период для генерации дат
        start_date = datetime(2025, 1, 1)
        end_date = datetime(2025, 12, 31)

        return {
            "id": str(uuid.uuid4()),
            "userId": user_id,
            "articleId": article_id,
            "downloadDate": self.fake.date_time_between(start_date, end_date).isoformat() + "Z",
            "format": random.choice(["PDF", "HTML"])
        }

#     def generate_download(self, user_id, article_id):
#         """Генерация тестовой загрузки с правильной структурой"""
#         # Получаем полные данные пользователя и статьи
#         user = self._get_item_by_id("users", user_id)
#         article = self._get_item_by_id("articles", article_id)
#
#         if not user or not article:
#             raise ValueError("User or article not found")
#
#         # Преобразуем publication_year в publicationYear и убедимся, что это int
#         article_data = {
#             "id": article["id"],
#             "doi": article["doi"],
#             "title": article["title"],
#             "author": article["author"],
#             "publicationYear": int(article["publication_year"]) if "publication_year" in article else 2023
#         }
#
#         # Задаем временной период
#         start_data = datetime(2025, 1, 1)
#         end_data = datetime(2025, 12, 31)
#
#         return {
#             "id": str(uuid.uuid4()),
#             "user": {
#                 "id": user["id"],
#                 "login": user["login"],
#                 "university": user["university"],
#                 "subscription_end_date": user["subscription_end_date"]
#             },
#             "article": article_data,
#
#             "downloadDate": self.fake.date_time_between(start_data, end_data).isoformat() + "Z",
# #             "downloadDate": self.fake.date_time_this_year().isoformat() + "Z", # генерит дату с 1 января до текущей даты
#             "format": self.fake.random_element(elements=("PDF", "HTML"))
#         }

    def _get_item_by_id(self, endpoint, item_id):
        """Получение полного объекта по ID"""
        try:
            response = requests.get(f"{self.base_url}/{endpoint}/{item_id}")
            response.raise_for_status()
            return response.json()
        except Exception as e:
            print(f"Ошибка при получении {endpoint} {item_id}: {str(e)}")
            return None

    def _get_existing_ids(self, endpoint):
        """Получение ID существующих записей"""
        try:
            response = requests.get(f"{self.base_url}/{endpoint}")
            response.raise_for_status()
            return [item['id'] for item in response.json()]
        except:
            return []

    def post_data(self, endpoint, data):
        """Отправка данных на сервер"""
        try:
            response = requests.post(
                f"{self.base_url}/{endpoint}",
                json=data,
                headers={'Content-Type': 'application/json'}
            )
            response.raise_for_status()
            return True
        except Exception as e:
            print(f"Ошибка при создании {endpoint}: {str(e)}")
            return False

    def generate_all(self, endpoint, count):
        """Основной метод генерации данных"""
        self.clear_data(endpoint)
        
        if endpoint == "users":
            generator = self.generate_user
        elif endpoint == "articles":
            generator = self.generate_article
        elif endpoint == "downloads":
            # Для загрузок нужны существующие пользователи и статьи
            users = self._get_existing_ids("users")
            articles = self._get_existing_ids("articles")
            if not users or not articles:
                print("Сначала создайте пользователей и статьи!")
                sys.exit(1)
            def download_generator():
                return self.generate_download(
                    random.choice(users),
                    random.choice(articles)
                )
#             def download_generator():
#                 return self.generate_download(
#                     self.fake.random_element(users),
#                     self.fake.random_element(articles)
#                 )
            generator = download_generator
        else:
            print(f"Неизвестный эндпоинт: {endpoint}")
            sys.exit(1)

        # Многопоточная генерация данных
        success = 0
        with ThreadPoolExecutor(max_workers=10) as executor:
            results = executor.map(
                lambda _: self.post_data(endpoint, generator()),
                range(count)
            )
            success = sum(results)
        
        print(f"Успешно создано {success} из {count} записей для {endpoint}")

def main():
    parser = argparse.ArgumentParser(description='Генератор тестовых данных для REST API')
    parser.add_argument('--count', type=int, default=500, help='Количество создаваемых объектов')
    parser.add_argument('--endpoint', required=True, choices=['users', 'articles', 'downloads'],
                       help='API endpoint  для генерации данных')
    parser.add_argument('--base-url', default='http://localhost:8080', help='Базовый URL API') # URL VM1(Server)
    
    args = parser.parse_args()
    
    generator = DataGenerator(args.base_url)
    generator.generate_all(args.endpoint, args.count)

if __name__ == "__main__":
    main()