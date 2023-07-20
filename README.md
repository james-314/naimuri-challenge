## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## First thoughts
- number of letters need to be n*n
- letters should only contain lower case
- no special chars or uppercase
- is there always a unique solution?
- is there always even a solution? for given case obviously but in general?
- if there is a unique letter then it must be on the main diagonal but not meant to use array form

## Solution approach
- get input and confirm suitability
- import word set and reduce. can use word length and letters required to shrink the set significantly probably
- apply the tbd algorithm to create a valid square. maybe some tree structure?