# Javagotchi: An AI-Driven Discord Cyber-Daemon

Javagotchi is a stateful, multi-threaded virtual pet daemon that lives exclusively within your Discord direct messages. Moving far beyond traditional command-and-response bots, it integrates a locally hosted Large Language Model (LLM) to give each pet species a unique, context-aware, and sentient personality. 

Built on a non-blocking, asynchronous Java architecture, Javagotchi simulates a living digital organism with its own biological heartbeat, offline decay, evolution mechanics, and permadeath. By running the AI locally, it ensures complete data privacy and operates as an air-gapped conversational engine.

---

## 🛠 Architecture & Technical Features

* **Neural Link (Local AI Integration):** Connects via REST to a locally hosted `llama3.2:1b` model using Ollama. This provides completely private, zero-telemetry conversational capabilities. The bot maintains a rolling short-term memory (hippocampus) of the last 10 messages for deep contextual awareness without bloating memory.
* **Biological Heartbeat & Offline Decay:** A dedicated `ScheduledExecutorService` thread manages pet vitals (Hunger and Happiness) on a strict 12-hour cycle. If the host server goes offline, the `StorageService` mathematically calculates missed ticks and applies precise offline decay upon the daemon's next boot, preventing state manipulation.
* **Permadeath Matrix:** Actions have irreversible consequences. If a pet's hunger or happiness drops to 0, the daemon succumbs to data decay. Its persistent JSON matrix, memories, and files are permanently eradicated from the host file system.
* **Evolution System:** Maintaining high happiness for 24 consecutive decay cycles triggers an evolution into a "super" variant, permanently increasing the daemon's maximum stat capacity.
* **Asynchronous Concurrency:** Utilizes `CompletableFuture` to handle AI generation on separate, non-blocking threads. This ensures the heavy lifting of LLM generation never blocks the Discord JDA Gateway, keeping the bot instantly responsive to native commands.
* **Polymorphic Brain:** Powered by an abstract `GeneralPet` core. Users choose between a Dog, Cat, Dragon, or Slime—each featuring unique starting stats, species behaviors, and specialized AI system prompts that dictate their distinct neural pathways.

---

## 🚀 Deployment & Installation Guide

Follow these comprehensive steps to deploy the Javagotchi daemon on your local environment.

### 1. Install System Prerequisites

You must have Java 17+ and Maven installed to compile the project.

**For Windows:**
* **Java 17:** Download and install the JDK from [Adoptium](https://adoptium.net/) or use `winget install Microsoft.OpenJDK.17`.
* **Maven:** Download from the [Apache Maven Project](https://maven.apache.org/download.cgi), extract it, and add the `bin` folder to your System Environment Variables (`PATH`).

**For macOS:**
* Using [Homebrew](https://brew.sh/):
  ```bash
  brew install openjdk@17
  brew install maven
  ```

**For Linux (Debian/Ubuntu):**
* Using APT:
  ```bash
  sudo apt update
  sudo apt install openjdk-17-jdk maven -y
  ```

*Verify your installation on any OS by running `java -version` and `mvn -version` in your terminal.*

### 2. Install & Configure the Local AI (Ollama)

Javagotchi requires a local instance of Ollama to act as its brain, ensuring your chat data never leaves your machine.

1. **Install Ollama:**
   * **Windows/macOS:** Download the installer directly from [ollama.com](https://ollama.com/).
   * **Linux:** Run the official install script: `curl -fsSL https://ollama.com/install.sh | sh`
2. **Start the Service:** Ensure the Ollama app is running in the background.
3. **Pull the Model:** Open your terminal and download the specific 1-billion parameter model optimized for this daemon:
   ```bash
   ollama pull llama3.2:1b
   ```
4. **Verify:** Open a web browser and navigate to `http://localhost:11434`. You should see the text "Ollama is running".

### 3. Setup the Discord Bot & Obtain Your Token

You need to register the application with Discord to generate a secure API key.

1. Navigate to the [Discord Developer Portal](https://discord.com/developers/applications).
2. Click **"New Application"** (top right), name your daemon (e.g., Javagotchi), and accept the terms.
3. On the left sidebar, click **"Bot"**.
4. **CRITICAL STEP:** Scroll down to **Privileged Gateway Intents**. You **MUST** toggle the following ON, or the daemon will crash on startup:
   * Presence Intent
   * Server Members Intent
   * **Message Content Intent** *(Required to read user commands)*
5. Click **"Save Changes"**.
6. Scroll back up to the "Token" section and click **"Reset Token"**. **Copy this generated token immediately** and store it securely. You will not be able to view it again.
7. **Invite the bot to your server:**
   * On the left sidebar, click **"OAuth2"** -> **"URL Generator"**.
   * Under "Scopes", select **`bot`**.
   * Under "Bot Permissions", select **`Read Messages/View Channels`** and **`Send Messages`**.
   * Copy the generated URL at the bottom, paste it into a new browser tab, and invite the bot to your testing server.

### 4. Setup the Environment Variables

To maintain security best practices, the bot reads the Discord token from your system's environment variables rather than hardcoded text.

**Windows (Command Prompt):**
```cmd
setx Discord_Token "YOUR_COPIED_TOKEN_HERE"
```
*(Note: You must completely restart your terminal or IDE after using `setx` for the changes to take effect).*

**macOS / Linux (Bash/Zsh):**
Add the following line to your `~/.bashrc` or `~/.zshrc` file:
```bash
export Discord_Token="YOUR_COPIED_TOKEN_HERE"
```
Then run `source ~/.bashrc` (or `.zshrc`).

**Using IntelliJ IDEA (Recommended IDE):**
1. Open the project folder in IntelliJ.
2. Near the top right Run button, click **Edit Configurations...**
3. Create a new "Application" configuration. Set `com.elliot.petbot.Main` as your Main Class.
4. Locate the **Environment variables** field and paste: `Discord_Token=YOUR_COPIED_TOKEN_HERE`.
5. Click Apply and OK.

### 5. Build and Run

Open a terminal in the root directory of the project (where the `pom.xml` file is located).

```bash
# Clean the project and compile all Maven dependencies
mvn clean compile

# Execute the Main class to boot the daemon
mvn exec:java -Dexec.mainClass="com.elliot.petbot.Main"
```
If successful, the terminal will display JDA log outputs confirming the bot has successfully authenticated and the master scheduler is online.

---

## 🎮 Interacting with Javagotchi

To maintain server cleanliness and a personal connection, all setup and AI interactions occur strictly within **Direct Messages (DMs)**.

### Initialization Flow
1. Go to the public Discord server where the bot resides.
2. Type `!createPet` in any channel.
3. The bot will establish a secure DM link with you.
4. Move to your Direct Messages with the bot.Type again `!createPet` in the private channell. Follow the 4-stage numeric setup wizard to select your daemon's biological framework (species) and assign its designation (name).

### Command Interface
Once initialization is complete (State 3), your daemon accepts the following precise commands:

| Command | System Action |
| :--- | :--- |
| `!status` | Outputs a detailed diagnostic report of current Hunger, Happiness, and upgrade status. |
| `!feed` | Injects data to restore the daemon's Hunger stat to maximum capacity. |
| `!play` | Engages the daemon, restoring the Happiness stat to maximum capacity. |
| `!sound` | Triggers the polymorphic audio response specific to your daemon's species. |
| `!stop` | Instantly aborts the `CompletableFuture` thread, cancelling the AI's current generation. |
| `!delete` | Flushes the `JsonArray` short-term memory and re-initializes the system prompt. |
| `!save` | Forces an immediate manual state snapshot to persistent `.txt` disk storage. |
| `!help` | Displays the terminal manual for available commands. |
| `!ping` | Network diagnostic. Responds with "Pong!". |

**Neural Chatting:**
Any DM sent to the bot that *does not* begin with the `!` prefix is routed directly to the Ollama local LLM. The AI will process your input and reply entirely in character based on the species' system blueprint!

---

## 📂 System Directory Structure

* **`Main.java`**: The core bootloader. Initializes the JDA instance and fires up the Master Scheduler thread for the 12-hour biological heartbeat.
* **`CommandListener.java`**: The global event dispatcher. Manages user routing, state-machine setup flows, and pushes messages to the asynchronous AI handler.
* **`OllamaClient.java`**: The local REST interface. Formats `JsonArray` memory banks and communicates directly with the local LLM endpoint on port `11434`.
* **`StorageService.java`**: The file I/O matrix. Handles serialization of JSON states, calculates offline decay chronometrics, and executes the permadeath (`eradicatePet`) sequence.
* **`UserSession.java`**: The RAM wrapper. Holds transient user states, the active asynchronous prompt thread, and the conversational history array.
* **`GeneralPet.java`**: The abstract blueprint. Enforces standard background logic, vitals tracking, and the evolution mechanism across all species.
* **`Stat.java`**: Encapsulated mathematical management for cleanly tracking percentages, bounds checking, and upgrading individual metrics.
* **`Dog.java`, `Cat.java`, `Dragon.java`, `Slime.java`**: Concrete polymorphic implementations defining unique system prompts and species variables.
