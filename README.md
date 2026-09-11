# Finanças Fácil — Android oficial

Este repositório contém duas aplicações Android nativas que incorporam localmente as interfaces web do Finanças Fácil:

- `v1/` — Finanças Fácil V1 (completa / próxima da planilha)
- `v2/` — Finanças Fácil V2 (experiência simplificada)

## Compilação

O workflow `.github/workflows/build-apks.yml` usa a cadeia oficial Android:
Android Gradle Plugin + AAPT2 + D8 + Android SDK Build Tools.

Ao executar o workflow, ele gera dois artefatos instaláveis:

- `FinancasFacil-V1-OFICIAL.apk`
- `FinancasFacil-V2-OFICIAL.apk`

Os aplicativos têm IDs diferentes (`br.com.financasfacil.v1` e `br.com.financasfacil.v2`) e podem ser instalados juntos.

## Requisitos definidos

- minSdk 24
- targetSdk 35
- compileSdk 35
- Activity exportada explicitamente
- Java 17
- WebView local/offline
