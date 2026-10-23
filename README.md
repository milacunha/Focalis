# Focalis ⏳

![Kotlin](https://img.shields.io/badge/Kotlin-2.4.20-D4A574?logo=kotlin&logoColor=white)
![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.12.1-A6463F?logo=jetpackcompose&logoColor=white)
![Android](https://img.shields.io/badge/Android-minSdk_24-B86B4F?logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-Suportado-4A6B52?logo=apple&logoColor=white)
![Desktop](https://img.shields.io/badge/Desktop-Windows-7EB6D4)
![Status](https://img.shields.io/badge/status-conclu%C3%ADdo-success)

> Um refúgio visual para o seu foco. Um temporizador multiplataforma com conexão ao YouTube e Spotify, temas aconchegantes e fundos imersivos.

O **Focalis** nasceu da necessidade de ter um ambiente de foco sem distrações. Ele roda nativamente em Android, iOS e Desktop (Windows) sem sincronização, sendo um companheiro silencioso para sessões de estudo e trabalho. O grande diferencial é a união de um timer preciso com a possibilidade de ouvir suas playlists favoritas enquanto a interface se adapta ao seu estado de espírito.

---

## 📸 Screenshots

| Tema Terracota | Tema Musgo |
|:---:|:---:|
| *[Adicione o print aqui]* | *[Adicione o print aqui]* |

| Tema Cachoeira | Tema Fogueira (Player Aberto) |
|:---:|:---:|
| *[Adicione o print aqui]* | *[Adicione o print aqui]* |

---

## ✨ O que o app faz

- ⏱️ **Timer customizável:** Durações de 1 a 60 minutos, com padrão de 15 min e pausa.
- 🛑 **Regra de inatividade:** Se o timer for pausado e não for retomado em 3 minutos, ele é zerado automaticamente e emite um alerta sonoro.
- 🔊 **Feedback sonoro:** Um som suave indica o fim do timer (ou o reset por inatividade).
- 💡 **Keep Screen On:** A tela do dispositivo permanece ligada enquanto o timer estiver rodando ou pausado.
- 🎵 **Integração de mídia:** Conexão com YouTube e Spotify embedados (apenas um player ativo por vez).
- 🎨 **4 Temas Imersivos:** Temas claro (Terracota e Musgo) e escuro (Cachoeira e Fogueira), cada um com sua própria paleta de cores e imagem de fundo.
- 🖥️ **Fallback inteligente:** No Desktop, o player de mídia abre no navegador padrão do sistema para não pesar a aplicação.

---

## 🛠️ Stack

- **[Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform.html)**
- **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)** (UI declarativa)
- **[Koin](https://insert-koin.io/)** (Injeção de Dependência)
- **[Coroutines & Flow](https://kotlinlang.org/docs/coroutines-guide.html)** (Assincronismo e Reatividade)
- **[Compose Resources](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-resources.html)** (Fontes e imagens multiplataforma)

---

## 🧠 Decisões Técnicas

Aqui estão os principais desafios arquiteturais e como eles foram resolvidos:

**1. Motor de Timer à prova de Drift (`TimerEngine`)**
A abordagem inicial de simplesmente subtrair 1 segundo a cada `delay(1000)` acumula atrasos (drift) em todas as plataformas. A solução foi criar um motor puramente baseado em `TimeSource.Monotonic`. O estado do timer guarda o `TimeMark` exato de término (`endTime`), e a UI apenas lê o relógio para calcular quanto falta. O *tick* da interface serve apenas para redesenhar a tela, nunca para contar o tempo.

**2. Regra de Inatividade de 3 Minutos**
Em vez de criar um segundo timer paralelo (que consumiria recursos em background), a inatividade é verificada de forma "lazy" no momento da retomada (`resume()`). Se o tempo desde a pausa for maior que 3 minutos, o `TimerEngine` simplesmente emite um evento de `ResetByInactivity` e volta ao estado inicial.

**3. Reprodução de Áudio Nativa (KMP)**
O som curto de finalização exigiu a implementação do contrato `SoundPlayer` usando as APIs nativas de cada plataforma: `SoundPool` (Android), `AVAudioPlayer` (iOS) e `javax.sound.sampled.Clip` (Desktop). Como o JVM é estrito com formatos, o arquivo `.wav` precisou ser convertido para PCM 16-bit para tocar em todas as plataformas.

**4. Player de Mídia e o Desafio do Desktop**
O YouTube e o Spotify são embedados usando `WebView` (Android) e `WKWebView` (iOS). Para o Desktop, a integração do Chromium (JCEF/KCEF) provou-se pesada e instável, exigindo mais de 200MB e dependências complexas. Como o app é focado em uso pessoal, a decisão técnica foi usar a API `java.awt.Desktop.browse()` como *fallback*, abrindo a mídia no navegador do sistema.

---

## 🎨 Design System

O Focalis foi desenhado para ter uma vibe **aconchegante, orgânica e de baixo contraste**, remetendo a leitura perto de uma lareira.

### Paleta de Cores e Fundos
Cada tema possui um fundo dedicado com um *scrim* (camada protetora) em gradiente para garantir a legibilidade.

| Tema | Fundo (Referência) | Cor de Destaque (Primary) | Vibe |
|---|---|---|---|
| **Terracota** | Linho claro | `#B86B4F` (Terracota) | Aconchego clássico |
| **Musgo** | Verde clarinho | `#4A6B52` (Floresta) | Natureza fresca |
| **Cachoeira** | Azul noite | `#7EB6D4` (Água) | Foco noturno |
| **Fogueira** | Marrom vulcânico | `#D4A574` (Âmbar) | Calor e mistério |

### Tipografia e Componentes
- **Timer Display:** *Sono* (Soft Monospace). Garante que os números não fiquem "dançando" na tela e traz um ar orgânico.
- **Interface em Geral:** *Nunito*. Uma fonte arredondada, amigável e altamente legível.
- **Botões:** Formato *pill* (20dp de raio) e transições animadas (efeito wipe horizontal entre Iniciar/Pausar).
- **Scrim:** Gradiente radial/vertical que mescla a imagem de fundo com a cor do tema para manter o timer centralizado e legível.

---

## 📁 Estrutura de Pastas

A arquitetura separa os apps nativos do código compartilhado e segue uma adaptação do Clean Architecture:

```text
focalis/
├── shared/                          # Módulo Kotlin Multiplatform
│   └── src/
│       ├── commonMain/kotlin/       # Lógica e UI compartilhada
│       │   └── br/com/camilacunha/focalis/
│       │       ├── domain/          # TimerEngine, modelos e regras
│       │       ├── presentation/    # ViewModels, temas e UI Compose
│       │       └── di/              # Módulos do Koin
│       ├── androidMain/             # Implementações Android (SoundPlayer, WebView)
│       ├── iosMain/                 # Implementações iOS (AVAudioPlayer, WKWebView)
│       └── jvmMain/                 # Implementações Desktop (JVM Sound, fallback web)
│
├── androidApp/                      # Entry point Android
├── desktopApp/                      # Entry point Desktop
└── iosApp/                          # Entry point Xcode (Swift)
```

---

## 🧪 Testes

O núcleo do aplicativo é blindado por testes unitários (rodando na JVM):
- **`TimerEngineTest`**: Valida a contagem exata sem drift, pausa, retomada, reset e a regra de inatividade de 3 minutos (usando `TestTimeSource`).
- **`TimerViewModelTest`**: Valida a emissão de eventos, o controle de estado do Koin e o disparo correto do `SoundPlayer` (usando um `FakeSoundPlayer`).

---

## 🚀 Como rodar

### Pré-requisitos
- JDK 17 ou superior
- Android Studio (com plugin Kotlin Multiplatform)
- Xcode (para rodar no iOS)
- Windows (para testar o *keep screen on* no Desktop)

### Instalação
```bash
# Clone o repositório
git clone https://github.com/milacunha/Focalis.git

# Abra no Android Studio e aguarde o Gradle Sync
```

### Rodando as plataformas
- **Android:** Selecione o módulo `androidApp` e rode no emulador ou dispositivo físico.
- **Desktop:** Execute `./gradlew :desktopApp:run` ou use a run configuration do Android Studio.
- **iOS:** Abra a pasta `iosApp` no Xcode e rode no simulador ou dispositivo (necessário configurar a Development Team).

> **Atenção no iOS:** Se você testar o player, o `KeepScreenOn` pode manter a tela ligada após o fim do timer (bug conhecido de `idleTimerDisabled` no Compose). A tela apagará normalmente ao sair do app.

---

## 📌 Status

O projeto está **concluído** e em uso pessoal. Não há planos para monetização ou publicação nas lojas oficiais, sendo um projeto de estudo e portfólio de arquitetura Kotlin Multiplatform.

### Known Issues
- **YouTube no Android:** O vídeo exibe apenas áudio e controles com a tela preta dentro do WebView (limitação do embed do YouTube e `Referer`). O Spotify funciona normalmente.

---

## ✒️ Autora

Feito com carinho por **[Camila Cunha]**.

- [LinkedIn](https://www.linkedin.com/in/camila-s-e-cunha/)
- [GitHub](https://github.com/milacunha)
