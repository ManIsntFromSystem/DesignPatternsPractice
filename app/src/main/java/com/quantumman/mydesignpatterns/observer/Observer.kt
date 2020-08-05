package com.quantumman.mydesignpatterns.observer

object WeatherStation {
    @JvmStatic
    fun main(args: Array<String>) {
        val weatherData = WeatherData()
        val currentDisplay: Observer =
            CurrentConditionsDisplay()
        weatherData.registerObserver(currentDisplay)
        weatherData.setMeasurements(29f, 65f, 745)
        weatherData.setMeasurements(39f, 70f, 760)
        weatherData.setMeasurements(42f, 72f, 763)
    }
}

internal interface Observer {
    fun update(temperature: Float, humidity: Float, pressure: Int)
}

internal interface Observable {
    fun registerObserver(o: Observer?)
    fun removeObserver(o: Observer?)
    fun notifyObservers()
}

internal class WeatherData :
    Observable {
    private val observers: MutableList<Observer> = mutableListOf()
    private var temperature = 0f
    private var humidity = 0f
    private var pressure = 0

    override fun registerObserver(o: Observer?) {
        o?.let{observers.add(it)}
    }

    override fun removeObserver(o: Observer?) {
        observers.remove(o)
    }

    override fun notifyObservers() {
        for (observer in observers) observer.update(temperature, humidity, pressure)
    }

    fun setMeasurements(
        temperature: Float,
        humidity: Float,
        pressure: Int
    ) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        notifyObservers()
    }
}

internal class CurrentConditionsDisplay :
    Observer {
    private var temperature = 0f
    private var humidity = 0f
    private var pressure = 0
    override fun update(
        temperature: Float,
        humidity: Float,
        pressure: Int
    ) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        display()
    }

    private fun display() {
        System.out.printf(
            "Сейчас значения:%.1f градусов цельсия и %.1f %% влажности. Давление %d мм рт. ст.\n",
            temperature,
            humidity,
            pressure
        )
    }
}