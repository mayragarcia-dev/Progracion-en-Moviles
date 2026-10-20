# Prompts utilizados

## Prompt 1
**Texto enviado a Gemini:** "Actúa como Tech Lead de Android. Necesito que mejores el código con buenas prácticas y diseño según esta interfaz de usuario. Esto está dirigido a mejorar mi código con IA, manteniendo los mismos conceptos básicos. Quiero que respondas en formato de código limpio. Ten en cuenta estas condiciones: No agregar código avanzado ni tampoco otra interfaz que no sea igual a la imagen enviada."

**Que genero la IA:** Generó la estructura base para la aplicación AyudaCampus usando la arquitectura MVVM, incluyendo las entidades de Room (Usuario, Ticket, Categoria, Comentario), sus DAOs, la abstracción del repositorio (`AyudaRepository` y `AyudaRepositoryRoom`), y los componentes de pantalla en Jetpack Compose con sus ViewModels.

**Que tuve que corregir yo:** Se corrigieron inconsistencias en el flujo de datos para garantizar que las pantallas Compose no accedieran directamente a los DAOs ni al Repositorio. Además, se ajustó la reactividad con `StateFlow` en los ViewModels (`BandejaViewModel`, `PanelViewModel`, `AtenderViewModel`) para procesar adecuadamente reglas de negocio como el cálculo de SLA, tiempo promedio de resolución y filtros combinados.

**Respeta MVVM?:** Sí. La arquitectura implementada respeta MVVM estrictamente: las Vistas (pantallas Compose) solo observan `StateFlow` expuesto por los ViewModels y envían eventos de usuario. Los ViewModels interactúan exclusivamente con la interfaz `AyudaRepository`, aislando por completo la base de datos Room de la capa de presentación.

## Reflexion (5 lineas)
- La IA mejoró la velocidad para crear código base de Room, entidades y layouts de Jetpack Compose, pero le costó mantener consistencia en la reactividad avanzada.
- El error principal fue generar acoplamientos sutiles entre la UI y la capa de datos sin considerar el cálculo reactivo de reglas de negocio en los ViewModels.
- Aprendí que en MVVM la UI debe ser completamente pasiva y solo reaccionar al estado emitido por `StateFlow`.
- Comprendí cómo Room debe ocultarse detrás de la interfaz `AyudaRepository` para permitir un código mantenible y desacoplado.
- Entendí cómo encapsular reglas de negocio (como métricas y SLA) dentro de la capa ViewModel para garantizar un diseño limpio y testeable.
