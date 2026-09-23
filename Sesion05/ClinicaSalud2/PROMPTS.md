# Registro de Prompts - Clínica Salud+

Este documento recopila los prompts y solicitudes principales utilizados durante el desarrollo asistido por IA de la aplicación **Clínica Salud+**, estructurado por etapas y mejoras.

## 1. Prompt inicial de desarrollo
> "Crear una aplicación Android llamada Clínica Salud+ usando Kotlin, Jetpack Compose y Material 3. La aplicación debe ser sencilla y apropiada para un proyecto académico..."

**Resultado:** Definición de restricciones, pantallas de Inicio, Perfil del médico, Agendar cita, Confirmación, Menú lateral (Drawer), Mis citas e Historial médico.

## 2. Solicitud de desarrollo por etapas
> "La aplicación debe desarrollarse por etapas, respetando los commits requeridos por la actividad. Después de realizar cada cambio funcional importante, debes detenerte y esperar mi confirmación..."

**Resultado:** Implementación dividida en 3 etapas principales con control estricto de commits.

## 3. Solicitud de documentación (README.md)
> "En el readme pon esto # Clínica Salud+..."

**Resultado:** Creación del archivo `README.md` con descripción, funcionalidades, tecnologías, estructura de carpetas, evidencias y créditos de la autora.

## 4. Prompt como Tech Lead de Android para refactorización
> "Actúa como Tech Lead de Android y ayúdame a mejorar el código de mi aplicación Clínica Salud+. El objetivo es mejorar la calidad, organización, legibilidad y buenas prácticas..."

**Resultado:** Refactorización interna del código en `MainActivity.kt`, creación del componente reutilizable `SelectionChip`, y optimización de estructura sin alterar la interfaz visual.

## 5. Ajustes de diseño, tonos y usuario principal
> "Fíjate en sus tonos de colores y diseño... Que Mayra García sea el usuario principal... Mejorar visual de historial médico y revisar la ruta que lleva a Perfil."

**Resultado:** 
* Desactivación del color dinámico en el tema (`Theme.kt`) para asegurar el color morado corporativo.
* Establecimiento de **Mayra García** como usuaria principal ("Hola, Mayra", iniciales "MG").
* Rediseño visual de la pantalla de **Historial médico** con tarjetas estructuradas y datos del paciente.
* Creación de la pantalla y ruta dedicada para el perfil del usuario (`PerfilUsuarioScreen` / `"perfil_usuario"`).

## An�lisis adicional de IA
