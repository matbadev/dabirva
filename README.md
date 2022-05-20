[![latest Maven Central release](https://maven-badges.herokuapp.com/maven-central/com.matbadev.dabirva/dabirva/badge.svg)](https://search.maven.org/artifact/com.matbadev.dabirva/dabirva)
[![publish workflow state](https://github.com/matbadev/dabirva/workflows/publish/badge.svg)](https://github.com/matbadev/dabirva/actions/workflows/publish.yml)
[![test coverage](https://codecov.io/gh/matbadev/dabirva/branch/master/graph/badge.svg?token=1HB0C8S7MN)](https://codecov.io/gh/matbadev/dabirva)
[![Apache 2 license](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

# Dabirva (Data Binding RecyclerView Adapter)

Dabirva is a simple and extensible adapter for Android's RecyclerView to easily build lists using the [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/).

Features:

- Seamless integration into the MVVM pattern ([recommended by Google](https://developer.android.com/jetpack/guide))
- Asynchronous item diffing by default for better performance
- Decorations binding
- Sticky headers (for horizontal and vertical linear layouts)
- Extensible classes

## Installation

Dabirva is available via the [Central Repository](https://central.sonatype.org/):

```groovy
dependencies {
    implementation "com.matbadev.dabirva:dabirva:1.0.0"
}
```

This library requires at least **Java 8**:

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

Furthermore **Kotlin 1.4+** and **Android SDK 21+** are required.

## Usage

### Defining item view models

To define an item view model (which can later be bound to a RecyclerView) start by implementing [ItemViewModel](dabirva/src/main/java/com/matbadev/dabirva/ItemViewModel.kt).
For a simple note this might look like this:

```kotlin
data class NoteViewModel(
    val id: Long,
    val text: String,
) : ItemViewModel {

    override val bindingId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.item_note

    override fun entityEquals(other: Any?): Boolean {
        return other is NoteViewModel && id == other.id
    }

}
```

Item view models need to be **bindable** which requires them to define a **binding ID** (from the generated ` BR`  class) and a **layout ID** (from the generated `R.layout` class).

Furthermore they need to be **diffable** which requires a proper ` equals` and `hashCode` implementation (e.g. by using a Kotlin data class) and an implementation of `entityEquals` which defines when two item view models describe the same entity (e.g. when they have the same ID).
Dabirva uses this information to detect item positions after list updates to display proper item animations.

The second step is to add the referenced layout with the view model's class as a variable.
For the previous note example this might look like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="viewModel"
            type="com.matbadev.dabirva.example.NoteViewModel" />
    </data>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@{viewModel.text}"
        tools:text="Some text" />

</layout>
```

### Binding item view models to a RecyclerView

To bind item view models to a RecyclerView using Dabirva as an adapter, first declare a list of [ItemViewModel](dabirva/src/main/java/com/matbadev/dabirva/ItemViewModel.kt) in your screen's view model using an observable type:

```kotlin
// Using ObservableField
val items = ObservableField<List<ItemViewModel>?>()

// Using NonNullObservableField (provided by Dabirva)
val items = NonNullObservableField<List<ItemViewModel>>(listOf())

// Using MutableLiveData
val items = MutableLiveData<List<ItemViewModel>?>()
```

Next bind the items to a RecyclerView in your XML layout:

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="viewModel"
            type="com.matbadev.dabirva.example.ActivityViewModel" />
    </data>

    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        app:dabirvaItems="@{viewModel.items}"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        tools:listitem="@layout/item_note" />

</layout>
```

When the `dabirvaItems` binding adapter is executed it will create an instance of `Dabirva` and attach it to the RecyclerView.
The displayed list will automatically be updated with proper item animations once the `items` field in the screen's view model is changed.

### Add decorations to RecyclerView items

Dabirva provides the `itemDecorations` binding adapter for adding decorations to RecyclerView items.
First they need to be defined in the screen's view model:

```kotlin
val itemDecorations = listOf<RecyclerView.ItemDecoration>(
    /* some item decorations */
)
```

After that they can easily be bound to a RecyclerView in the screen's XML layout:

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="viewModel"
            type="com.matbadev.dabirva.example.ActivityViewModel" />
    </data>

    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        app:itemDecorations="@{viewModel.itemDecorations}"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        tools:listitem="@layout/item_note" />

</layout>
```

This can be done without using the `dabirvaItems` binding adapter.

#### Sticky headers

Dabirva supports sticky list headers for horizontal and vertical linear layouts.
**This requires an instance of `Dabirva` to be used as RecyclerView adapter.**

To use sticky headers simply add a corresponding instance to the `itemDecorations` field in the screen's view model:

```kotlin
val itemDecorations = listOf<RecyclerView.ItemDecoration>(
    HorizontalStickyHeaderDecoration(
        headerPositionProvider = ItemHeaderProvider { it is HeaderViewModel },
    ),
)
```

As you can see you need to provide a [HeaderPositionProvider](dabirva/src/main/java/com/matbadev/dabirva/decoration/HeaderPositionProvider.kt) for defining the items to use as headers.
The easiest way is to implement [ItemHeaderProvider](dabirva/src/main/java/com/matbadev/dabirva/decoration/ItemHeaderProvider.kt) which just requires a predicate to decide if a specific item should be used as a header.

### Advanced: use custom `Executor` for item diffing

To perform the item diffing process on a custom `Executor` provide a suitable factory to [DabirvaConfig](dabirva/src/main/java/com/matbadev/dabirva/DabirvaConfig.kt) which is used by the `dabirvaItems` binding adapter.
This can also be used to execute the diffing synchronously on the main thread:

```kotlin
DabirvaConfig.factory = DabirvaFactory { Dabirva(diffExecutor = Runnable::run) }
```

This might cause UI lags for complex lists and is therefore not recommended.

### Advanced: extend the `Dabirva` class

Dabirva supports subclassing the `Dabirva` class to add custom functionality:

```kotlin
class CustomDabirva : Dabirva() {
    // Add custom logic here by overwriting the correspondent methods.
}
```

To make the `dabirvaItems` binding adapter instantiate your custom class instead of the default `Dabirva` one you need to supply a suitable factory to [DabirvaConfig](dabirva/src/main/java/com/matbadev/dabirva/DabirvaConfig.kt):

```kotlin
DabirvaConfig.factory = DabirvaFactory { CustomDabirva() }
```

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
