import os
import psycopg2
import uuid
import random
#import faker
from faker import Faker
import random
import uuid
from datetime import datetime, timedelta

# Подключение к базе данных PostgreSQL
def connect_db():

    return psycopg2.connect(
        host=os.getenv('DB_HOST', 'localhost'),
        port=os.getenv('DB_PORT', '5432'),
        dbname=os.getenv('DB_NAME', 'articles'),
        user=os.getenv('DB_USER', 'admin'),
        password=os.getenv('DB_PASSWORD', 'secret')
    )

#     return psycopg2.connect(
#         dbname="articles", # Имя вашей базы данных
#         user="admin",      # Имя пользователя
#         password="secret", # Ваш пароль
# #         host="host.docker.internal",  # Адрес хоста
#         host="localhost",  # Адрес хоста
#         port="5432"        # Порт PostgreSQL
#     )

# Функция для генерации случайных данных для таблицы t_user
def generate_users(n):
    fake = Faker()
    users = []
    for _ in range(n):
        user_id = str(uuid.uuid4())
        login = fake.user_name()
        university = fake.company()
        subscription_end_date = (datetime.now() + timedelta(days=random.randint(30, 365))).date()
        users.append((user_id, login, university, subscription_end_date))
    return users

# Функция для генерации случайных данных для таблицы t_article
def generate_articles(n):
    fake = Faker()
    articles = []
    for _ in range(n):
        article_id = str(uuid.uuid4())
        doi = f"10.{random.randint(1000, 9999)}/{fake.uuid4()[:8]}"
        title = fake.sentence(nb_words=6)
        author = fake.name()
        publication_year = random.randint(2000, 2024)
        articles.append((article_id, doi, title, author, publication_year))
    return articles

# Функция для генерации случайных данных для таблицы t_download
def generate_downloads(users, articles, n):
    downloads = []
    formats = ["PDF", "HTML"]
    for _ in range(n):
        user_id = random.choice(users)[0]
        article_id = random.choice(articles)[0]
        download_date = datetime.now() - timedelta(days=random.randint(1, 30))
        format_ = random.choice(formats)
        downloads.append((str(uuid.uuid4()), user_id, article_id, download_date, format_))
    return downloads

# Основная функция для заполнения базы данных
def populate_db():
    conn = connect_db()
    cursor = conn.cursor()

    # Генерируем данные
    users = generate_users(10)
    articles = generate_articles(20)
    downloads = generate_downloads(users, articles, 50)

    # Вставляем данные в таблицу t_user
    for user in users:
        cursor.execute("""
            INSERT INTO t_user (id, login, university, subscription_end_date)
            VALUES (%s, %s, %s, %s)
        """, user)

    # Вставляем данные в таблицу t_article
    for article in articles:
        cursor.execute("""
            INSERT INTO t_article (id, doi, title, author, publication_year)
            VALUES (%s, %s, %s, %s, %s)
        """, article)

    # Вставляем данные в таблицу t_download
    for download in downloads:
        cursor.execute("""
            INSERT INTO t_download (id, user_id, article_id, download_date, format)
            VALUES (%s, %s, %s, %s, %s)
        """, download)

    # Сохраняем изменения и закрываем соединение
    conn.commit()
    cursor.close()
    conn.close()

# Запуск функции
if __name__ == "__main__":
    populate_db()
