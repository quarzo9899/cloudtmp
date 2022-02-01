FROM python:3.6

RUN mkdir /code  
WORKDIR /code  
COPY . .  
RUN pip install -r requirements.txt  

CMD ["python", "/code/script.py"]`