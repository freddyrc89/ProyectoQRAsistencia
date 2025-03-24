from flask import Flask, request, jsonify
from datetime import datetime

app = Flask(__name__)

# Lista en memoria para almacenar los invitados temporalmente
invitados = []
id_counter = 1  # asigna ID único a cada invitado


# ---------- RUTA DE BIENVENIDA ----------
@app.route('/')
def home():
    return jsonify({"mensaje": "Bienvenido a la API de Registro de Invitados"}), 200


# ---------- RUTA PARA REGISTRAR INVITADOS ----------
@app.route('/invitado', methods=['POST'])
def registrar_invitado():
    global id_counter  # Para manejar el ID

    datos = request.get_json()

    # Validación básica
    nombre = datos.get('nombre')
    apellido = datos.get('apellido')

    if not nombre or not apellido:
        return jsonify({"error": "Nombre y apellido son obligatorios"}), 400

    # Obtener la fecha y la hora actual automáticamente
    fecha_hora_registro = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    # Crear un nuevo invitado en memoria
    nuevo_invitado = {
        "id": id_counter,
        "nombre": nombre,
        "apellido": apellido,
        "fecha_hora_registro": fecha_hora_registro  # Se guarda automáticamente
    }

    invitados.append(nuevo_invitado)  # Guardar en la lista en memoria
    id_counter += 1  # Incrementar el ID para el próximo invitado

    return jsonify({
        "mensaje": "Invitado registrado correctamente",
        "invitado": nuevo_invitado
    }), 201


# ---------- RUTA PARA LISTAR INVITADOS ----------
@app.route('/invitados', methods=['GET'])
def listar_invitados():
    return jsonify({"invitados": invitados})


# ---------- EJECUCIÓN DE LA APP ----------
if __name__ == '__main__':
    app.run(debug=True)
