## Description

<img src="/imgs/demo.gif" width="240" align="right" hspace="20">

This project is to showcase the modern approach to the architecture and  
design of the Android project. It shows how to leverage libraries and  
frameworks when developing an Android application. This project might  
appear simple but it packs enough functionality to uncover and not  
complicate things too much.

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

This project follows the principles of Clean Architecture any source of  
data including API, DB, UI is separated from each other by the middle  
layer.

![Architecture](/imgs/arch.jpg)

## Dependency injection

Dagger is the industry standard for dependency injection recognized by  
Google, stable and allows to scale projects easily as they grow.

![Dependency injection](/imgs/di.jpg)

## Sudoku solver algorithm

![Algorithm](/imgs/algo.jpg)

## Implementation thoughts

This time I went deep with coroutines, and the approach is very  
interesting. The design is very slick although error-handling and  
canceling is not that obvious but tools are there. Structured  
concurrency is awesome!

## Roadmap
- Improve UI design
- Different color for guess
- Improve navigation pattern
- Better animation of SudokuView
- Invalid sudoku handling
- OCR sudoku handling
- UI tests
- CI
- Static analyzers
- R8 Rules
- Split into feature modules