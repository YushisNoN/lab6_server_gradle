import socket

# Настройки сервера
HOST = '127.0.0.1'  # Адрес сервера
PORT = 2205       # UDP-порт сервера

# Создаем UDP-сокет
with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
    # Отправляем сообщение
    message = b"Hello, UDP Server!"
    s.sendto(message, (HOST, PORT))
    print(f"Отправлено: {message.decode('utf-8')}")
    
    # Получаем ответ
    data, addr = s.recvfrom(1024)
    print(f"Получен ответ от {addr}: {data.decode('utf-8')}")
