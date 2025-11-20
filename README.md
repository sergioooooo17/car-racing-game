# car-racing-game

### Description of the game :

Trzyosobowy wyścig 2D. Każdy gracz steruje własnym samochodem i ściga się do linii mety:

- BMW X6M (czarny) – sterowanie strzałkami.
- Mercedes W200 (srebrny) – sterowanie klawiszami **WASD**.
- Mercedes C klasa (biały) – sterowanie klawiszami **IJKL**.

Wygra ten, kto jako pierwszy dojedzie do żółtej linii mety po prawej stronie.

### How to start the game from source

```bash
javac Game.java
java Game
```

### Build a Windows .exe (requires Windows + JDK 17+ with `jpackage`)

1. Zainstaluj JDK 17 lub nowsze na Windows (pakiet musi zawierać narzędzie `jpackage`).
2. W PowerShell przejdź do katalogu projektu i (jeśli trzeba) zezwól na uruchamianie skryptów w bieżącej sesji: `Set-ExecutionPolicy -Scope Process RemoteSigned`.
3. Uruchom: `./scripts/build-windows-exe.ps1`
4. Gotowy plik znajdziesz w `dist/CarRacingGame.exe`. Dystrybucja zawiera wbudowane JRE, więc można ją uruchomić na komputerze bez zainstalowanej Javy.

### Snapshots

![Snapshot 1](https://github.com/return007/car-racing-game/blob/master/images/readme1.png "Snapshot 1")<br>
![Snapshot 2](https://github.com/return007/car-racing-game/blob/master/images/readme2.png "Snapshot 2")
