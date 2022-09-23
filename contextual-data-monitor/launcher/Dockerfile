FROM python:3-alpine

WORKDIR /usr/src/app
COPY requeriments.txt ./
RUN pip install --no-cache-dir -r requeriments.txt

COPY . .

CMD [ "python", "./main.py" ]
