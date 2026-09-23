package com.tuapp.navlab.model

data class Student(
    val id: Int,
    val name: String,
    val career: String,
    val studentId: String = "2024-0001",
    val email: String = "juan.leon@example.com",
    val phone: String = "987654321",
    val cycle: String = "5to Ciclo",
    val faculty: String = "Ingeniería y Tecnología",
    val biography: String = "Estudiante destacado con interés en desarrollo Android."
)

val sampleStudents = listOf(
    Student(
        id = 1,
        name = "Juan León",
        career = "Ingeniería de Sistemas",
        studentId = "2024-0001",
        email = "juan.leon@example.com",
        phone = "987654321",
        cycle = "5to Ciclo",
        faculty = "Ingeniería y Tecnología",
        biography = "Estudiante destacado con interés en desarrollo Android."
    ),
    Student(
        id = 2,
        name = "Maria Garcia",
        career = "Arquitectura",
        studentId = "2024-0002",
        email = "maria.garcia@example.com",
        phone = "976543210",
        cycle = "5to Ciclo",
        faculty = "Arquitectura y Diseño",
        biography = "Apasionada por el diseño de espacios sostenibles y urbanismo."
    ),
    Student(
        id = 3,
        name = "Carlos Perez",
        career = "Medicina",
        studentId = "2024-0003",
        email = "carlos.perez@example.com",
        phone = "965432109",
        cycle = "6to Ciclo",
        faculty = "Ciencias de la Salud",
        biography = "Interesado en investigación médica y pediatría."
    ),
    Student(
        id = 4,
        name = "Ana Lopez",
        career = "Derecho",
        studentId = "2024-0004",
        email = "ana.lopez@example.com",
        phone = "954321098",
        cycle = "3er Ciclo",
        faculty = "Derecho y Ciencias Políticas",
        biography = "Enfocada en derecho corporativo e internacional."
    ),
    Student(
        id = 5,
        name = "Luis Ramirez",
        career = "Administración",
        studentId = "2024-0005",
        email = "luis.ramirez@example.com",
        phone = "943210987",
        cycle = "4to Ciclo",
        faculty = "Negocios y Gestión",
        biography = "Entusiasta del emprendimiento y la gestión de proyectos."
    )
)
