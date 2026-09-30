# Laboratorio 06: DropdownMenu y NavigationDrawer en TECSUP Store

**Nombre:** Mayra García
**Descripción:** Aplicación TECSUP Store desarrollada en Jetpack Compose que implementa tarjetas de producto interactivas con menú contextual (`DropdownMenu` de 3 puntos) y navegación lateral avanzada (`NavigationDrawer` con cabecera de usuario y destinos activos resaltados).

> **Nota:** Este proyecto es la continuación y evolución del Laboratorio 04 (Carrito de Compras).

## Funcionalidades Implementadas (Laboratorio 06)
- [x] **`TarjetaProducto.kt`:** Ícono de 3 puntos (⋮) con estado `expanded` y `DropdownMenu` desplegable ("Favoritos", "Compartir", "Reportar") equipado con `leadingIcon` personalizados.
- [x] **`AppDrawer.kt`:** Contenido del `NavigationDrawer` usando `ModalDrawerSheet`, incluyendo encabezado de usuario con avatar/iniciales ("MG"), nombre, correo y resaltado visual del ítem activo.
- [x] **`AppNavegacion.kt`:** Estructura de navegación principal envuelta con `ModalNavigationDrawer`, barra superior (`TopAppBar`) y botón ☰ para apertura del menú lateral.

## Hitos de Desarrollo y Commits
1. **Hito 1:** `feat: agregar ícono de 3 puntos y estado expanded en la tarjeta de producto`
2. **Hito 2:** `feat: implementar DropdownMenu con opciones básicas en la tarjeta de producto`
3. **Hito 3:** `feat: personalizar DropdownMenu con leadingIcon y colores en la tarjeta`
4. **Hito 4:** `feat: crear estructura del NavigationDrawer con ModalDrawerSheet`
5. **Hito 5:** `feat: implementar navegación real desde los ítems del drawer en AppNavegacion`
6. **Hito 6:** `feat: personalizar encabezado de usuario e indicador visual de ítem activo en el drawer`

---

## Base del Proyecto: Laboratorio 04 (Carrito de Compras)

**Descripción:** Aplicación de carrito de compras desarrollada en Jetpack Compose que permite agregar productos, visualizarlos en una lista, eliminarlos y calcular subtotales e IGV (18%).

### Respuestas Conceptuales (Lab 04)

#### (a) ¿Por qué `mutableStateListOf` y no una `MutableList` normal?
Una `MutableList` normal no es observable por Compose. Si agregas o eliminas elementos de una `MutableList` estándar, Compose no detectará el cambio y no recompondrá la UI. `mutableStateListOf` crea una lista observable que notifica a Compose cada vez que su contenido cambia.

#### (b) ¿Por qué la lista se declara con `val`?
Se declara con `val` porque la **referencia** a la lista no cambia (siempre es el mismo objeto lista creado por `remember`). Lo que cambia es el **contenido** interno de la lista (sus elementos).

#### (c) ¿Qué hace `weight(1f)` en la `LazyColumn`?
Indica que el componente debe ocupar todo el espacio vertical sobrante, permitiendo que la `LazyColumn` se expanda en el centro y mantenga el panel de totales fijo en la parte inferior.
