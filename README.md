# CocktailsTDD

Android pet project built around a test-first workflow. The app loads cocktails from the public CocktailDB API, maps network DTOs into domain models, and renders a Compose image grid with search, alcohol filtering, loading, empty, and error states.

The main goal of this repository is to keep the production code small and explicit while showing how Android UI, data, DI, and presentation logic can be developed through focused tests.

## Stack

- Kotlin
- Jetpack Compose
- Navigation 3
- Hilt
- Retrofit
- Coil
- JUnit4
- Compose UI tests

## Architecture

The project is intentionally split by responsibility:

- `domain` contains cocktail models and pure filtering/search rules.
- `data/network` contains Retrofit API loading and network DTO mapping.
- `data/repository` coordinates data loading for the domain layer.
- `features/list/presentation` contains ViewModel MVI state, actions, and loading behavior.
- `features/list/ui` contains Compose UI and small reusable controls.
- `di` wires the production graph with Hilt.

Use cases are not added as one-line proxy classes. The project keeps behavior where it currently pays for itself: pure domain rules, repository loading, ViewModel state transitions and UI rendering.

## Tests

The project uses a small test stack on purpose:

- JUnit4 for domain, data, repository, DI, ViewModel, and integration tests.
- Compose UI tests for the list screen and `MainActivity`.
- MockWebServer for Retrofit network and ViewModel integration tests.

Run JVM tests:

```bash
./gradlew :app:testDebugUnitTest
```

Build the instrumented test APK:

```bash
./gradlew :app:assembleDebugAndroidTest
```

Run Compose UI tests on a connected device or emulator:

```bash
./gradlew :app:connectedDebugAndroidTest
```

## Development Notes

The repository history is structured as small TDD-oriented slices: domain rules first, then mapping, repository loading, network source, ViewModel MVI, DI, Navigation 3 Activity wiring, and UI behavior.

There is no CI configuration yet. Room/cache support is intentionally left for a separate feature slice so it can be implemented with its own tests.
