import os

filename = input('파일 이름을 입력해주세요: ')

path = f'./{filename}.txt'

if not os.path.exists(path):
    exit()

with open(path, 'r', encoding='utf-8') as file:
    data = file.read()

data = data.split('\n')
while data[-1] == '':
    data.pop(-1)

dictionary = {}
for line in data:
    key, value = line.split('=')
    dictionary[key] = value

print(dictionary)

with open(path, 'w', encoding='utf-8') as file:
    keys = list(dictionary.keys())
    keys.sort()
    for key in keys:
        file.write(f'{key}={dictionary[key]}\n')
