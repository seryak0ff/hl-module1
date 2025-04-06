import psycopg2
from psycopg2 import sql

# Параметры подключения к базе данных
DB_HOST = "localhost"  # Хост
DB_NAME = "articles"   # Имя базы данных "museum"
DB_USER = "admin"      # Имя пользователя
DB_PASSWORD = "secret" # Пароль

# Создаем подключение к базе данных
def connect_db():
    return psycopg2.connect(
        dbname="articles",  # Имя вашей базы данных
        user="admin",  # Имя пользователя
        password="secret",  # Ваш пароль
        host="localhost",  # Адрес хоста
        port="5432"  # Порт PostgreSQL
    )

# Функция для очистки базы данных
def clean_db():
    conn = connect_db()
    cursor = conn.cursor()

    # Очистка таблиц download, article, user
    cursor.execute("TRUNCATE TABLE t_download, t_article, t_user RESTART IDENTITY CASCADE;")
    print("Таблицы t_download, t_article, t_user очищены")

#     # Очистка таблицы flyway_schema_history
#     cursor.execute("TRUNCATE TABLE flyway_schema_history RESTART IDENTITY CASCADE;") # TRUNCATE => DELETE FROM
#     print("Таблица flyway_schema_history очищена")

    # Сохраняем изменения и закрываем соединение
    conn.commit()
    cursor.close()
    conn.close()

# Запуск очистки
if __name__ == "__main__":
    clean_db()
