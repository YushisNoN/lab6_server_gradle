import socket

# Настройки сервера
HOST = '127.0.0.1'  # Слушаем все интерфейсы
PORT = 2205       # UDP-порт

# Создаем UDP-сокет
with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
    s.bind((HOST, PORT))
    print(f"UDP-сервер запущен на {HOST}:{PORT}")
    
    while True:
        # Получаем данные и адрес клиента
        data, addr = s.recvfrom(1024)
        print(f"Получено от {addr}: {data.decode('utf-8')}")
        
        # Отправляем ответ
        s.sendto(b"Message received", addr)
