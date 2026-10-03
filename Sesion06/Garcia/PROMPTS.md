# Repositorio de Prompts para Mejora de Código Android con IA

Este documento recopila prompts especializados diseñados para actuar con el rol de **Tech Lead de Android**, orientados a refactorizar, optimizar y mejorar código existente basándose en buenas prácticas, arquitectura limpia y fidelidad estricta a interfaces de usuario (UI) proporcionadas mediante imágenes o mockups.

---

## 1. Prompt Principal (Refactorización y UI basada en imagen)

**Uso:** Ideal para enviar tu código actual junto con una captura de pantalla de la interfaz deseada para obtener una versión limpia, estructurada y alineada con las buenas prácticas de Jetpack Compose y Android.

```text
Actúa como Tech Lead de Android. Necesito que mejores el código con buenas prácticas y diseño según esta interfaz de usuario. 

Esto está dirigido a mejorar mi código con IA, manteniendo los mismos conceptos básicos. 

Quiero que respondas en formato de código limpio. 

Ten en cuenta estas condiciones: 
1. No agregar código avanzado ni tampoco otra interfaz que no sea igual a la imagen enviada.
2. Mantener la arquitectura y el flujo lógico actual del código base.
3. Aplicar principios SOLID, legibilidad y separación de responsabilidades adecuada en Jetpack Compose.
```

---

## 2. Prompt de Revisión de Componentes y Jetpack Compose

**Uso:** Útil cuando necesitas optimizar componentes específicos de Compose (como tarjetas, listas o pantallas completas) asegurando reutilización y rendimiento sin sobreingeniería.

```text
Actúa como Tech Lead de Android especialista en Jetpack Compose. Revisa y refactoriza el siguiente código para cumplir con los estándares de diseño y buenas prácticas de UI.

Condiciones estrictas:
- Respeta fielmente la estructura visual mostrada en la imagen de referencia adjunta.
- Mantén exactamente los mismos conceptos básicos y modelo de datos del código original.
- No introduzcas dependencias externas innecesarias, animaciones complejas ni características avanzadas no solicitadas.
- Entrega únicamente código limpio, comentado donde sea estrictamente necesario y listo para producción.
```
