# Prompts utilizados

## Registro de uso

Sí se utilizó Gemini para apoyar la mejora de AyudaCampus. El prompt general de abajo reproduce aproximadamente el texto compartido por la estudiante. No se conservaron aquí la respuesta exacta de Gemini ni los archivos concretos que propuso, así que no se atribuyen a Gemini cambios específicos sin evidencia. Los prompts técnicos de A, B y C son propuestas de seguimiento y no se registran como enviados.

## Prompt utilizado · Mejora del código según la interfaz

**Texto enviado a Gemini (aproximado, según lo compartido):**

> Actúa como Tech Lead de Android. Necesito que mejores el código con buenas prácticas y diseño según esta interfaz de usuario. Esto está dirigido a mejorar mi código con IA, manteniendo los mismos conceptos básicos. Quiero que respondas en formato de código limpio. Ten en cuenta estas condiciones: No agregar código avanzado ni tampoco otra interfaz que no sea igual a la imagen enviada.

**Qué generó la IA:** Gemini se utilizó como apoyo para mejorar el código según la indicación y la interfaz de referencia. No se conservó el detalle de la respuesta ni un listado de los archivos sugeridos.

**Qué tuve que corregir yo:** No se documentaron correcciones concretas a la respuesta de Gemini. En la implementación de Fase 2 se revisó que las pantallas consuman estado de sus ViewModels y que el ViewModel use AyudaRepository.

**¿Respeta MVVM?:** No se puede evaluar la respuesta específica de Gemini porque no se conservó. La implementación actual fue revisada: las Views no acceden a DAO ni Repository y el ViewModel usa la interfaz AyudaRepository.

## Propuestas de prompts técnicos pendientes

Los siguientes prompts son sugerencias para documentar o enviar en otra sesión; no se afirman como prompts que ya se enviaron a Gemini.

### Prompt A propuesto · Tiempo promedio de resolución

> Tengo una app Kotlin con Jetpack Compose, Material 3, MVVM, Room y un AyudaRepository (interface). Agrega en el panel de soporte el tiempo promedio de resolución de los tickets cerrados (fechaCierre − fecha). La consulta va en TicketDao, la función se declara primero en AyudaRepository y se implementa en AyudaRepositoryRoom; PanelViewModel expone el valor como StateFlow y PanelScreen solo lo muestra. No uses XML ni accedas a DAO o Repository desde la View. Muestra los archivos que cambian.

**Qué generó la IA:** Pendiente; este prompt técnico es una propuesta y no se registra como enviado. En la implementación actual, Codex añadió la consulta Room, el contrato e implementación del Repository, el StateFlow del ViewModel y su visualización en el panel.

**Qué tuve que corregir yo:** Pendiente de registrar si se envía este prompt.

**¿Respeta MVVM?:** Pendiente para este prompt. La implementación actual respeta MVVM: Room queda detrás de AyudaRepository, el ViewModel depende de la interfaz y la pantalla solo observa estado.

### Prompt B propuesto · Contador SLA por prioridad

**Prompt propuesto:**

> En mi app Kotlin con Jetpack Compose y MVVM, muestra en cada ticket de la bandeja de soporte el tiempo restante del SLA según su prioridad: Alta 4 horas, Media 24 horas y Baja 72 horas. Si se vence, muestra cuánto tiempo lleva excedido. Calcula y actualiza el estado en BandejaViewModel usando los tickets expuestos por AyudaRepository; la pantalla solo presenta ese estado. No uses DAO ni Repository desde la View. Muestra los archivos que cambian.

**Qué generó la IA:** Pendiente; este prompt no se registra como enviado. En la implementación actual, Codex añadió el cálculo y actualización del SLA al ViewModel y su presentación en la bandeja.

**Qué tuve que corregir yo:** No aplica a Gemini. Se verificó que la UI consuma el mapa de estado preparado por el ViewModel.

**¿Respeta MVVM?:** Pendiente de evaluar la respuesta de Gemini. La implementación actual usa AyudaRepository en el ViewModel y deja la presentación a Compose.

### Prompt C propuesto · Búsqueda avanzada en la bandeja

**Prompt propuesto:**

> En mi app Kotlin con Jetpack Compose, Room, MVVM y AyudaRepository (interface), agrega búsqueda combinada en la bandeja de soporte por prioridad, categoría y texto. El texto debe buscar en título, descripción, cliente y ubicación. Conserva el filtro por estado. Obtén categorías activas mediante AyudaRepository y combina los filtros en BandejaViewModel; la View solo muestra controles y tickets filtrados. No uses DAO ni Repository desde la View. Muestra los archivos que cambian.

**Qué generó la IA:** Pendiente; este prompt no se registra como enviado. En la implementación actual, Codex implementó los estados y combinación de filtros en BandejaViewModel y los controles Compose.

**Qué tuve que corregir yo:** No aplica a Gemini. Se comprobó que texto, prioridad, categoría y estado se apliquen simultáneamente.

**¿Respeta MVVM?:** Pendiente de evaluar la respuesta de Gemini. La implementación actual accede al Repository solo desde el ViewModel; BandejaScreen consume StateFlow.

## Reflexión

- Se utilizó Gemini con un prompt general para mejorar el código según la interfaz compartida.
- El texto del prompt quedó registrado de forma aproximada; no se conservaron la salida ni los archivos exactos sugeridos por Gemini.
- El cálculo del promedio consulta Room detrás del contrato AyudaRepository.
- El SLA y la búsqueda combinada se exponen desde BandejaViewModel y las pantallas solo los presentan.
- Revisar las dependencias por capa ayuda a mantener MVVM y a evitar que la UI acceda a Room directamente.
