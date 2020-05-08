## Description

<img src="/imgs/demo.gif" width="240" align="right" hspace="20">

This project is to showcase approach to architecture and design an app.  
It uncovers modern techniques and libraries to design and developAndroid  
applications. This project might appear simple but it packs just enough  
functionality to showcase and to not complicate modern approach.

## Tech stack

- Coroutines - background operations
- Dagger2 - dependency injection
- Retrofit - networking
- Room - data storage
- Jetpack
  - LiveData - lifecycle aware streams to notify UI about state changes
  - ViewModel - manage presentation data in lifecycle and config changes
  aware manner
- Testing
  - Mockito + Mockito-Kotlin

## Architecture

This project follows the principles of Clean Architecture any source of data
including API, DB, UI are separated from each other by the middle layer.

![Architecture](/imgs/arch.jpg)

## Dependency injection

Dagger now is the industry standard for dependency injection recognized  
by Google, stable and allows to scale project easily as it grows.

![Dependency injection](/imgs/di.jpg)

## Sudoku solver algorithm

![Algorithm](/imgs/algo.jpg)

## Implementation thoughts

This time I wend deep with coroutines, and the approach is very interesting.
The design is very slick although error-handling and cancelling is not that
obvious but tools are there. Structured concurrency is awesome!

## Roadmap
- Improve UI design
- Different color for guess
- Improve navigation pattern
- Better animation of SudokuView
- Invalid sudoku handling
- OCR sudoku handling
- UI tests
- CI
- Static analysers
- R8 Rules
- Split into feature modules