from flask_sqlalchemy import SQLAlchemy

# Crear una instancia de SQLAlchemy
db = SQLAlchemy()

# Modelo para la tabla 'alumnos'
class Alumnos(db.Model):
    __tablename__ = 'alumnos'

    id = db.Column(db.Integer, primary_key=True)
    dni = db.Column(db.String(10), unique=True, nullable=False)
    nombre = db.Column(db.String(255), nullable=False)
    programa_estudios = db.Column(db.String(255), nullable=False)
    estado = db.Column(db.Enum('A', 'D', name='estado_enum'), nullable=False)
    observaciones = db.Column(db.Text, nullable=True)
    password = db.Column(db.String(50), nullable=True)

    def __repr__(self):
        return f"<Alumnos {self.nombre}>"

# Modelo para la tabla 'accesos'
class Accesos(db.Model):
    __tablename__ = 'accesos'

    id = db.Column(db.Integer, primary_key=True)
    dni = db.Column(db.String(10), db.ForeignKey('alumnos.dni'), nullable=False)
    fecha_hora = db.Column(db.TIMESTAMP, default=db.func.current_timestamp(), nullable=False)
    estado_acceso = db.Column(db.Enum('PERMITIDO', 'DENEGADO', name='estado_acceso_enum'), nullable=False)
    observaciones = db.Column(db.Text, nullable=True)
    qr_creado = db.Column(db.TIMESTAMP, default=db.func.current_timestamp(), nullable=False)
    qr_expira = db.Column(db.TIMESTAMP, nullable=False)

    alumno = db.relationship('Alumnos', backref=db.backref('accesos', lazy=True))

    def __repr__(self):
        return f"<Accesos {self.dni}>"

# Modelo para la tabla 'configuracion'
class Configuracion(db.Model):
    __tablename__ = 'configuracion'

    id = db.Column(db.Integer, primary_key=True)
    tiempo_caducidad = db.Column(db.Integer, nullable=False, default=3)

    def __repr__(self):
        return f"<Configuracion {self.tiempo_caducidad}>"

# Modelo para la tabla 'invitados'
class Invitados(db.Model):
    __tablename__ = 'invitados'

    id = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(100), nullable=False)
    apellido = db.Column(db.String(100), nullable=False)
    created_at = db.Column(db.TIMESTAMP, default=db.func.current_timestamp(), nullable=True)

    def __repr__(self):
        return f"<Invitados {self.nombre} {self.apellido}>"

# Modelo para la tabla 'vigilante'
class Vigilante(db.Model):
    __tablename__ = 'vigilante'

    id = db.Column(db.Integer, primary_key=True)
    dni = db.Column(db.String(10), nullable=False)
    nombre = db.Column(db.String(255), nullable=False)
    password = db.Column(db.String(255), nullable=False)

    def __repr__(self):
        return f"<Vigilante {self.nombre}>"

# Modelo para la tabla 'User' (para autenticación)
class User(db.Model):
    __tablename__ = 'users'

    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80), unique=True, nullable=False)
    password = db.Column(db.String(120), nullable=False)

    def __repr__(self):
        return f"<User {self.username}>"
