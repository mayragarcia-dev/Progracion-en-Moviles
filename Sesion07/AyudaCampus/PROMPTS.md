# Prompts utilizados

## Registro de uso

En esta sesión no se enviaron prompts a Gemini en Android Studio. Los cambios A, B y C fueron implementados con Codex; por eso no se atribuye a Gemini código ni respuestas que no produjo. Los textos siguientes son propuestas pendientes de enviar y no cuentan como prompts usados.

## Prompt 1 · Tiempo promedio de resolución

**Texto enviado a Gemini:** No enviado.

**Prompt preparado (pendiente):**

> Tengo una app Kotlin con Jetpack Compose, Material 3, MVVM, Room y un AyudaRepository (interface). Agrega en el panel de soporte el tiempo promedio de resolución de los tickets cerrados (fechaCierre − fecha). La consulta va en TicketDao, la función se declara primero en AyudaRepository y se implementa en AyudaRepositoryRoom; PanelViewModel expone el valor como StateFlow y PanelScreen solo lo muestra. No uses XML ni accedas a DAO o Repository desde la View. Muestra los archivos que cambian.

**Qué generó la IA:** No hubo salida de Gemini. Codex implementó la consulta Room, el contrato e implementación del Repository, el StateFlow del ViewModel y su visualización en el panel.

**Qué tuve que corregir yo:** No aplica a Gemini. Se revisó que la nueva operación estuviera declarada en AyudaRepository antes de implementarla y usarla.

**¿Respeta MVVM?:** No evaluable para Gemini porque no respondió. La implementación revisada respeta MVVM: Room queda detrás de AyudaRepository, el ViewModel depende de la interfaz y la pantalla solo observa estado.

## Prompt 2 · Contador SLA por prioridad

**Texto enviado a Gemini:** No enviado.

**Prompt preparado (pendiente):**

> En mi app Kotlin con Jetpack Compose y MVVM, muestra en cada ticket de la bandeja de soporte el tiempo restante del SLA según su prioridad: Alta 4 horas, Media 24 horas y Baja 72 horas. Si se vence, muestra cuánto tiempo lleva excedido. Calcula y actualiza el estado en BandejaViewModel usando los tickets expuestos por AyudaRepository; la pantalla solo presenta ese estado. No uses DAO ni Repository desde la View. Muestra los archivos que cambian.

**Qué generó la IA:** No hubo salida de Gemini. Codex añadió el cálculo y actualización del SLA al ViewModel y su presentación en la bandeja.

**Qué tuve que corregir yo:** No aplica a Gemini. Se verificó que la UI consuma el mapa de estado preparado por el ViewModel.

**¿Respeta MVVM?:** No evaluable para Gemini porque no respondió. La implementación revisada usa la API de AyudaRepository en el ViewModel y deja la presentación a Compose.

## Prompt 3 · Búsqueda avanzada en la bandeja

**Texto enviado a Gemini:** No enviado.

**Prompt preparado (pendiente):**

> En mi app Kotlin con Jetpack Compose, Room, MVVM y AyudaRepository (interface), agrega búsqueda combinada en la bandeja de soporte por prioridad, categoría y texto. El texto debe buscar en título, descripción, cliente y ubicación. Conserva el filtro por estado. Obtén categorías activas mediante AyudaRepository y combina los filtros en BandejaViewModel; la View solo muestra controles y tickets filtrados. No uses DAO ni Repository desde la View. Muestra los archivos que cambian.

**Qué generó la IA:** No hubo salida de Gemini. Codex implementó los estados y combinación de filtros en BandejaViewModel y los controles Compose.

**Qué tuve que corregir yo:** No aplica a Gemini. Se comprobó que texto, prioridad, categoría y estado se apliquen simultáneamente.

**¿Respeta MVVM?:** No evaluable para Gemini porque no respondió. La implementación revisada accede al Repository solo desde el ViewModel; BandejaScreen consume StateFlow.

## Reflexión

- Gemini no estuvo disponible en Android Studio durante esta sesión, así que no produjo cambios para comparar.
- Codex implementó las tres mejoras solicitadas y este documento no las presenta como trabajo de Gemini.
- El cálculo del promedio consulta Room detrás del contrato AyudaRepository.
- El SLA y la búsqueda combinada se exponen desde BandejaViewModel y las pantallas solo los presentan.
- Revisar las dependencias por capa ayuda a mantener MVVM y a evitar que la UI acceda a Room directamente.
