## Planning poker

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

### Service specification

The specification document for this application can be found [here](./software-requiremenets-specification.md).

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
