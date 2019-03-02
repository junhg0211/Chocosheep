import socket
import _thread

def recv_thread(sock, buff):
    while True:
        print(str([sock.recv(buff).decode()])[2:-2])

def send_thread(sock):
    while True:
        sock.send((input() + '\r\n').encode())

if __name__ == '__main__':
    host, port = input('Insert Server Host and Port: ').split(':')
    port = int(port)

    s = socket.socket()
    s.connect((host, port))

    _thread.start_new_thread(recv_thread, (s, 1024))
    send_thread(s)