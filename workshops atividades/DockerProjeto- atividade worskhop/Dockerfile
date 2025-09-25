# Teste para versão do Python 3.11
FROM python:3.11-slim
# Teste para versão do Python 3.8
#FROM python:3.8-slim

# Diretório de trabalho
WORKDIR /app

# Copiando os requisitos para a imagem
COPY requirements.txt requirements.txt

# Instalando as dependências necessárias para o código
RUN pip install --no-cache-dir -r requirements.txt

# Copiando o código para a imagem
COPY . .

# Expondo a porta 5000 do container 
EXPOSE 5000

# Usando a biblioteca GUnicorn (Servidor WSGI) para executar a aplicação na web
CMD ["gunicorn", "--bind", "0.0.0.0:5000", "app.main:app"]
