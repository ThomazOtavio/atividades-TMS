from flask import Flask
import sys
import logging

app = Flask(__name__)
log_file = 'logs/access.log'

# Configuração básica para os logs
# Descreva aqui sua solução para o arquivo de log ser compartilhado com o host (Atividade de Volume)
# Solução:
logging.basicConfig(filename=log_file, level=logging.INFO,
                    format='%(asctime)s [%(levelname)s] - %(message)s', datefmt='%Y-%m-%d %H:%M:%S')

logging.info("NOVA INSTÂNCIA DO CONTÊINER INICIADA")

# Simulação de configurações base da aplicação
config_padrao = {
    "recurso_A_habilitado": True,
    "tema": "dark",
    "log_level": "INFO"
}

# Simulação de configurações específicas do usuário
config_usuario = {
    "tema": "light",
    "id_usuario": 12345
}


@app.route('/config')
def get_config():
    # O operador '|' foi introduzido no Python 3.9, sendo assim ele vai funcionar perfeitamente,
    # em casos onde a instalação seja dessa versão adiante, se o servidor de produção não tiver
    # alinhado, então teremos um problema de recurso, já que versões anteriores não ofereceram
    # suporte a esse operador

    logging.warning(f'Novo acesso detectado. Plataforma: {sys.platform}')

    try:
        # Funciona apenas para Python > 3.9
        config = config_padrao | config_usuario

        # Funciona perfeitamente para Python <= 3.8
        # config = {}
        # config.update(config_padrao)
        # config.update(config_usuario)

        return {
            "mensagem": "Configurações carregadas com sucesso!",
            "python_version": sys.version.split(" ")[0],
            "config": config
        }
    except TypeError as e:
        return f"""
        <h1>Erro na Aplicação!</h1>
        <p><strong>Versão do Python:</strong> {sys.version.split(" ")[0]}</p>
        <p><strong>Erro:</strong> {e}</p>
        <p><strong>Causa provável:</strong> O código usa uma feature incompatível com esta versão do Python.</p>
        """, 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
