# **Focalis - Pomodoro Multiplataforma**  

**🚀 Um cronômetro do método Pomodoro, construído com Kotlin Multiplatform (KMP) e Compose Multiplatform (CMP), rodando em 4 plataformas:**  
- 📱 **Android**  
- 🍏 **iOS**  
- 🖥️ **Desktop** (Windows, macOS, Linux)  
- 🌐 **Web**  

---

## **✨ Tecnologias Principais**  
- **💻 Kotlin Multiplatform (KMP)** – Código compartilhado entre todas as plataformas  
- **🎨 Jetpack Compose Multiplatform (CMP)** – UI declarativa e unificada  
- **📱 Compose for Android, iOS, Desktop e Web** – Mesmo design em todas as plataformas  
- **⚡ Coroutines + Flow** – Gerenciamento assíncrono eficiente  

---

## **📌 Funcionalidades**  
✔ **Cronômetro Pomodoro clássico e presonalizável** (25min trabalho + 5min pausa)  
✔ **Modos personalizáveis** (Foco, Ciclos, Intervalos)  
✔ **Responsivo** – Adapta-se a smartphones, tablets e desktops  

---

## **📲 Plataformas Suportadas**  
| **Plataforma**  | **Status** | **Observação** |  
|----------------|-----------|----------------|  
| **Android**    | ✅ 100%   | Jetpack Compose Nativo |  
| **iOS**        | ✅ 100%   | Compose via Kotlin/Native |  
| **Desktop**    | ✅ 100%   | Windows, macOS e Linux |  
| **Web**        | ✅ 100%   | Compose for Web (Canvas) |  

---

## **🎨 Design & Arquitetura**  
- **Padrão MVI (Model-View-Intent)** para gerenciamento de estado  
- **UI 100% Compose** – Zero XML, zero SwiftUI  

---

## **📂 Estrutura do Projeto**  
```
focalis/
├── shared/           # Código KMP comum (ViewModel, lógica de negócios)
│   ├── src/commonMain
│   ├── src/androidMain
│   └── src/iosMain
├── androidApp/       # Aplicativo Android
├── iosApp/           # Aplicativo iOS (SwiftUI bridge se necessário)
├── desktopApp/       # Aplicativo Desktop (JVM)
└── webApp/           # Aplicativo Web (Kotlin/JS)
```

---

## **🚀 Roadmap Futuro**  
- [ ] **Implementação dos tempos ativo e descanso**
- [ ] **Implementação dos ciclos**
- [ ] **Personalização dos tempos ativo e descanso**
- [ ] **Personalização dos ciclos**
- [ ] **Banco de dados local** (Room)
- [ ] **Estatísticas de ciclos**
- [ ] **Implementar pausa de notificações**
- [ ] **Implementar tela sempre ligada**
- [ ] **Adicionar animações e sons de efeito** (UI)
- [ ] **Implementar login** (Firebase)
- [ ] **Sincronização em nuvem** (Firebase)
- [ ] **Teste unitários** (Android)
- [ ] **Testes instrumentados** (Android)

---

## 📝 Autora

Projeto estruturado, desenvolvido e documentado por **Camila Cunha**

- [Github](https://github.com/milacunha)
- [LinkedIn](https://www.linkedin.com/in/camila-s-e-cunha/)
