# Voyager [![Build Status](https://travis-ci.org/juanmbellini/voyager.svg?branch=master)](https://travis-ci.org/juanmbellini/voyager)

Second part of the fourth System Simulations project: The voyager mission simulation.

## Getting started

These instructions will install the system in your local machine.

### Prerequisites

1. Clone the repository, or download source code

	```
	$ git clone https://github.com/juanmbellini/voyager
	```
	or

	```
	$ wget https://github.com/juanmbellini/voyager/archive/master.zip
	```

2. Install Maven, if you haven't yet

    #### Mac OS X

    ```
    $ brew install maven
    ```

    #### Ubuntu

    ```
    $ sudo apt-get install maven
    ```

    #### Other OSes
    Check [Maven website](https://maven.apache.org/install.html).


### Installing

1. Change working directory to project root (i.e where pom.xml is located):

    ```
    $ cd <project-root>
    ```

2. Let maven resolve dependencies:

    ```
    $ mvn dependency:resolve -U
    ```

3. Create jar file

    ```
    $ mvn clean package
    ```
    **Note:** The jar file will be under ``` <project-root>/target ```


## Usage

You can run the simulation with the following command:


```
$ java -jar <path-to-jar> [arguments]
```

## Authors

- [Juan Marcos Bellini](https://github.com/juanmbellini)
- [Matías Fraga](https://github.com/matifraga)
