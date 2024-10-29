package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.TemparatureSensorAvro;
import ru.yandex.practicum.telemetry.collector.KafkaEventProducer;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.model.sensor.TemperatureSensorEvent;

@Component
public class TemperatureEventHandler extends BaseSensorHandler {

    public TemperatureEventHandler(KafkaEventProducer kafkaEventProducer) {
        super(kafkaEventProducer);
    }

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    TemparatureSensorAvro toAvro(SensorEvent sensorEvent) {
        TemperatureSensorEvent temperatureEvent = (TemperatureSensorEvent) sensorEvent;

        return TemparatureSensorAvro.newBuilder()
                .setTemperatureF(temperatureEvent.getTemperatureF())
                .setTemparatureC(temperatureEvent.getTemperatureC())
                .build();
    }

}
