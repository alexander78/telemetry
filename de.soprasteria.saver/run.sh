#!/bin/bash

# Script de compilation et d'exécution pour l'Assistant de Gestion ABC INTER

echo "═══════════════════════════════════════════════════════════"
echo "  Compilation de l'Assistant de Gestion ABC INTER"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Créer le répertoire de build
BUILD_DIR="build"
SRC_DIR="src"

if [ -d "$BUILD_DIR" ]; then
    rm -rf "$BUILD_DIR"
fi

mkdir -p "$BUILD_DIR"

# Compiler le code source - trouver tous les fichiers .java
echo "📦 Compilation en cours..."
find "$SRC_DIR" -name "*.java" > /tmp/sources.txt
javac -d "$BUILD_DIR" @/tmp/sources.txt

if [ $? -eq 0 ]; then
    echo "✅ Compilation réussie!"
    echo ""
    echo "═══════════════════════════════════════════════════════════"
    echo "  Lancement de l'application"
    echo "═══════════════════════════════════════════════════════════"
    echo ""
    
    # Lancer l'application
    cd "$BUILD_DIR"
    java de.soprasteria.saver.Starter
else
    echo "❌ Erreur lors de la compilation"
    exit 1
fi
