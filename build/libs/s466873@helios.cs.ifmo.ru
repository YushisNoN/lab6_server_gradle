import socket

# Настройки сервера
HOST = 'localhost'  # Локальный адрес
PORT = 2206        # Произвольный свободный порт

# Создаем TCP-сокет
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    # Привязываем сокет к адресу и порту
    s.bind((HOST, PORT))
    # Начинаем слушать соединения (макс. 1 подключение в очереди)
    s.listen(1)
    print(f"Сервер запущен на {HOST}:{PORT}. Ожидание подключения...")
    
    # Принимаем соединение
    conn, addr = s.accept()
    with conn:
        print(f"Подключен клиент {addr}")
        while True:
            # Получаем данные (максимум 1024 байта)
            data = conn.recv(1024)
            if not data:
                break
            print(f"Получено: {data.decode('utf-8')}")
            # Отправляем ответ
            conn.sendall("Сообщение получено")
