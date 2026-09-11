# Finanças Fácil — Android oficial

Este repositório contém duas aplicações Android instaláveis:

- `v1/` — Finanças Fácil V1, completa e próxima da planilha original.
- `v2/` — Finanças Fácil V2, com experiência simplificada e preenchimento progressivo.

## APKs

O workflow `.github/workflows/build-apks.yml` usa Android Gradle Plugin, AAPT2, D8 e Android SDK Build Tools para gerar:

- `FinancasFacil-V1.apk`
- `FinancasFacil-V2.apk`

Os aplicativos têm IDs diferentes (`br.com.financasfacil.v1` e `br.com.financasfacil.v2`) e podem ser instalados juntos no mesmo aparelho.

## Requisitos

- minSdk 24
- targetSdk 35
- compileSdk 35
- Activity exportada explicitamente
- Java 17
- WebView local/offline

Build automático configurado para a branch `main`.
