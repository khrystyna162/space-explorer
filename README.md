# Space Explorer

Text-based space exploration game where players can explore the solar system, collect resources, and manage their inventory.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Technical Details](#technical-details)
- [Getting Started](#getting-started)
- [Game Mechanics](#game-mechanics)
- [Documentation](#documentation)

## Features
- 🚀 Solar system exploration
- 🔐 Player authentication system
- 📦 Inventory management
- 🌍 Multiple sectors with various space objects
- 💎 Resource collection system
- 📝 Detailed information about celestial objects

## Project Structure
```
src/
├── main/
│   └── java/
│       └── com/
│           ├── spaceexplorer/
│           │   ├── config/          # Configuration loading
│           │   ├── model/           # Game entities
│           │   ├── repository/      # Data persistence
│           │   ├── service/         # Business logic
│           │   └── ui/              # User interface
│           └── resources            # Game resources
└── test/
    ├── java/
    │    └── com/
    │       └── spaceexplorer/       # Test classes
    └── resources                    # Test resources
```

## Technical Details

### Architecture
The application follows a layered architecture:

| Layer | Purpose | Key Components |
|-------|---------|----------------|
| UI | User interaction | ConsoleUI |
| Service | Business logic | AuthService, PasswordHasher |
| Repository | Data persistence | PlayerRepository, GameRepository, ItemRepository |
| Model | Domain objects | Player, Item, GameMap, Sector, SpaceObject |

### Technologies Used
- Java 17
- Jackson for JSON handling
- SLF4J for logging
- JUnit for testing
- YAML for configuration

## Getting Started

### Prerequisites
- Java 17 or higher
- Text editor or IDE (IntelliJ IDEA recommended)

## Game Mechanics

### Available Commands
- Login/Register
- Explore sectors
- Collect resources
- Manage inventory
- View player status
- Exit game

### Resource Types
| Type | Description | Found In |
|------|-------------|----------|
| IRON | Basic metal | Planets, Asteroids |
| WATER | Essential resource | Planets |
| OXYGEN | Life support | Space Stations |
| MINERALS | Various minerals | Asteroids |

### Space Objects
- **Planets**: Large celestial bodies with various resources
- **Asteroids**: Small objects rich in minerals
- **Space Stations**: Artificial structures with unique resources

### Code Examples

#### Player Creation
```java
Player player = new Player("username", "password");
player.setHealth(100);
player.setInventorySize(10);
```

#### Resource Collection
```java
Item resource = new Item("Iron", "RESOURCE");
player.addItem(resource);
```

## Documentation

### API Documentation
Full JavaDoc documentation is available in the `docs/javadoc` directory.

### UML Diagrams
- File diagram: `docs/diagrams/SpaceExplorerDiagram.puml`


## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License
This project is licensed under the MIT License.