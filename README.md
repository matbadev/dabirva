# Dabirva (Data Binding RecyclerView Adapter)

![https://search.maven.org/artifact/com.matbadev.dabirva/dabirva](https://maven-badges.herokuapp.com/maven-central/com.matbadev.dabirva/dabirva/badge.svg)
![https://github.com/matbadev/dabirva/actions/workflows/publish.yml](https://github.com/matbadev/dabirva/workflows/publish/badge.svg)
![https://codecov.io/gh/matbadev/dabirva](https://codecov.io/gh/matbadev/dabirva/branch/master/graph/badge.svg?token=1HB0C8S7MN)
![https://www.apache.org/licenses/LICENSE-2.0.html](https://img.shields.io/badge/License-Apache_2.0-blue.svg)

Dabirva is a simple and extensible adapter for Android's RecyclerView to easily build lists using [Data Binding](https://developer.android.com/topic/libraries/data-binding/).

## Installation

Dabirva is available via the [Central Repository](https://central.sonatype.org/):

```groovy
dependencies {
    implementation "com.matbadev.dabirva:dabirva:1.0.0"
}
```

This library requires at least Java 8:

```groovy
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8
    }
}
```

## Usage

TODO

## License

```
Copyright 2021 Matthias Bäuerle

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
