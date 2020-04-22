## Planning poker

[![License: WTFPL](https://img.shields.io/badge/License-WTFPL-red.svg)](http://www.wtfpl.net/txt/copying/)

### Building

To build executable jar, run:

```bash
mvn clean package
```

### Running

After the application is done building, there should be a `.jar` file in `target` directory.
To execute this file, run:

```bash
java -jar target/planningpoker-*.jar
```

### Configuration

See `com.glovoapp.planningpoker.Configuration` for all available environment variables.
