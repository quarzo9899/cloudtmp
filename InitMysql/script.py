import os
import mysql.connector
import time


time.sleep(65)   

password = os.environ['MY_PASS']

mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  password=password,
  database="user"
)

mycursor = mydb.cursor()

mycursor.execute("DROP TABLE utente;")

mycursor.execute("CREATE TABLE utente (id bigint NOT NULL, is_admin bit NOT NULL, mail varchar(255) NOT NULL, password varchar(255) NOT NULL, PRIMARY KEY (id), UNIQUE (mail));")

mycursor.execute('INSERT INTO utente VALUES (0, 0, "admin", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");')


myresult = mycursor.fetchall()

for x in myresult:
  print(x)

print("Running with user: %s" % username)