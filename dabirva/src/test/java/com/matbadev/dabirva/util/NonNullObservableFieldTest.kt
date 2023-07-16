package com.matbadev.dabirva.util

import androidx.databinding.Observable
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NonNullObservableFieldTest {

    @Test
    fun `GIVEN non-null initial value WHEN creating instance EXPECT success`() {
        val initialValue = 42
        NonNullObservableField(initialValue)
    }

    @Test
    fun `GIVEN non-null initial value WHEN creating instance and accessing value EXPECT value`() {
        val value = "text"
        val observableField = NonNullObservableField(value)
        assertEquals(value, observableField.value)
    }

    @Test
    fun `GIVEN null initial value WHEN creating instance EXPECT exception`() {
        val nullRef = AtomicReference<Any>(null)
        assertFailsWith<NullPointerException> {
            NonNullObservableField(nullRef.get())
        }
    }

    @Test
    fun `GIVEN non-null new value WHEN setting value EXPECT value update`() {
        val observableField = NonNullObservableField("text")
        var propertyChangedValue: String? = null
        observableField.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            @Suppress("UNCHECKED_CAST")
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                val field = sender as NonNullObservableField<String>
                propertyChangedValue = field.value
            }
        })
        val newValue = "updatedText"
        observableField.value = newValue
        assertEquals(newValue, observableField.value)
        assertEquals(newValue, propertyChangedValue)
    }

    @Test
    fun `GIVEN null new value WHEN setting value EXPECT exception`() {
        val nullStringRef = AtomicReference<String>(null)
        val observableField = NonNullObservableField("")
        assertFailsWith<NullPointerException> {
            observableField.value = nullStringRef.get()
        }
    }

}
