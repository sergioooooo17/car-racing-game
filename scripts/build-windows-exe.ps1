$ErrorActionPreference = "Stop"

# Change to project root (folder containing this script)
Set-Location (Split-Path -Parent $MyInvocation.MyCommand.Path)

$buildDir = Join-Path $PWD "build"
$distDir = Join-Path $PWD "dist"

# Clean old artifacts
if (Test-Path $buildDir) { Remove-Item -Recurse -Force $buildDir }
if (Test-Path $distDir) { Remove-Item -Recurse -Force $distDir }

Write-Host "Compiling sources..."
javac -d $buildDir Game.java

Write-Host "Creating runnable JAR..."
jar --create --file (Join-Path $buildDir "car-racing-game.jar") -C $buildDir .

Write-Host "Packaging Windows EXE with jpackage..."
jpackage `
    --input $buildDir `
    --name "CarRacingGame" `
    --main-jar "car-racing-game.jar" `
    --main-class Game `
    --type exe `
    --dest $distDir

Write-Host "Executable created at:" (Join-Path $distDir "CarRacingGame.exe")
