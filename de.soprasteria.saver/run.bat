@echo off
REM Script de compilation et d'execution pour l'Assistant de Gestion ABC INTER

echo ═══════════════════════════════════════════════════════════
echo   Compilation de l'Assistant de Gestion ABC INTER
echo ═══════════════════════════════════════════════════════════
echo.

REM Creer le repertoire de build
set BUILD_DIR=build
set SRC_DIR=src

if exist %BUILD_DIR% rmdir /s /q %BUILD_DIR%
mkdir %BUILD_DIR%

REM Compiler le code source
echo 📦 Compilation en cours...
javac -d %BUILD_DIR% -sourcepath %SRC_DIR% %SRC_DIR%\de\soprasteria\saver\Starter.java %SRC_DIR%\de\soprasteria\saver\**\*.java

if %ERRORLEVEL% EQU 0 (
    echo ✅ Compilation reussie!
    echo.
    echo ═══════════════════════════════════════════════════════════
    echo   Lancement de l'application
    echo ═══════════════════════════════════════════════════════════
    echo.
    
    REM Lancer l'application
    cd %BUILD_DIR%
    java de.soprasteria.saver.Starter
) else (
    echo ❌ Erreur lors de la compilation
    exit /b 1
)
