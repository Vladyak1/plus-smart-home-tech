package ru.yandex.practicum.telemetry.collector.service.handler.sensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.TemparatureSensorAvro;
import ru.yandex.practicum.telemetry.collector.configuration.KafkaEventProducer;

@Component
public class TemperatureEventHandler extends BaseSensorHandler {

    private static final Logger log = LoggerFactory.getLogger(TemperatureEventHandler.class);

    public TemperatureEventHandler(KafkaEventProducer kafkaEventProducer) {
        super(kafkaEventProducer);
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    TemparatureSensorAvro toAvro(SensorEventProto sensorEvent) {
        log.info("Converting to Avro Temperature sensor event: {}", sensorEvent);
        TemperatureSensorEvent temperatureEvent = sensorEvent.getTemperatureSensorEvent();

        return TemparatureSensorAvro.newBuilder()
                .setTemperatureF(temperatureEvent.getTemperatureF())
                .setTemparatureC(temperatureEvent.getTemperatureC())
                .build();
    }

}
