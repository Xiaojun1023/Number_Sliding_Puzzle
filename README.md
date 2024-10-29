
# Number Sliding Puzzle

A classic Android-based 3x3 number sliding puzzle game designed to provide an engaging and challenging experience. Players can slide tiles into the empty space to arrange them in sequential order. The app tracks player statistics, including best time and completion count, making it a fun, personalized game.

## Features

- **3x3 Sliding Puzzle**: Classic number puzzle with a 3x3 grid where players slide tiles to solve the puzzle.
- **Randomized Puzzle Layout**: Each game starts with a randomized, solvable layout for a fresh challenge every time.
- **Player Statistics Tracking**: Tracks each player's best completion time and number of times completed.
- **Persistent Data Storage**: Uses SharedPreferences to save player statistics locally, so data is available across sessions.
- **Timer and Elapsed Time Display**: Displays the elapsed time during gameplay and tracks the player’s best time.
- **Animation**: Includes flip animations when records are updated and smooth tile movements.
- **Personalized Player Experience**: Stores each player’s name and retrieves their stats on future sessions.

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- **Android Studio**: Make sure you have Android Studio installed.
- **Android Device or Emulator**: Required to run the app.

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Xiaojun1023/NumberSlidingPuzzle.git

2. **Open the Project in Android Studio**:
   Launch Android Studio.
   Go to File > Open and navigate to the cloned repository folder.
   Select the project to open it.

3. **Build the Project**:
   Wait for Android Studio to synchronize and build the project.
   Resolve any dependencies if prompted.

4. **Run the App**:
   Click the Run button (green arrow) in Android Studio.
   Choose your connected Android device or emulator.
   The app should now install and launch on your device.

## Usage

### Main Features

1. **Starting a Game**:
   On the main screen, enter your name and press the Start Game button.
   If you're a returning player, your previous statistics (best time and completion count) will be displayed.

2. **Playing the Puzzle**:
   Once the puzzle appears, slide tiles by tapping them to move them into the empty space.
   Arrange the tiles in sequential order to complete the puzzle.

3. **Tracking Elapsed Time**:
   A timer will display your current elapsed time during the game.
   The best time will be updated if you beat your previous record.

4. **Checking Completion Stats**:
   Upon completion, the app displays your time and increments the completion count.

5. **Restarting the Game**:
   Tap the Restart button to reset the puzzle to a new, randomized layout.

## Built With
- Java: Main programming language for app development.
- Android Studio: Official IDE for Android development.
- XML: Used for designing UI layouts.

## Contributing

1. **Fork the Repositor**:
   Click the Fork button at the top right corner of the repository page.

2. **Clone Your Forked Repository**:
   ```bash
   git clone https://github.com/Xiaojun1023/NumberSlidingPuzzle.git

3. **Create a New Branch**:
   git checkout -b feature-name

4. **Make Your Changes**:
   Implement your feature or fix bugs.
   Ensure your code follows the project's coding standards.

5. **Commit Your Changes**:
   git commit -m "Description of your changes"

6. **Push to the Branch**:
   git push origin feature-name

7. **Create a Pull Reques**:
   Go to the original repository on GitHub.
   Click on Pull Requests and then New Pull Request.
   Compare your branch with the original and submit the pull request for review.

## Acknowledgments

- Android Developers Community: For the extensive documentation and tutorials.
- Open-Source Libraries: For providing useful tools and resources.
- Inspiration: Traditional flashcard apps and educational tools.

  Enjoy solving the Number Sliding Puzzle and enhancing your Android development skills!
