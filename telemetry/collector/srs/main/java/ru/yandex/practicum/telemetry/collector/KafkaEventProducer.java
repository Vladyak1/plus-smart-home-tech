package ru.yandex.practicum.telemetry.collector;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.telemetry.collector.configuration.KafkaConfig;

@Component
@Getter @Setter @ToString
public class KafkaEventProducer {
    private final KafkaProducer<String, SpecificRecordBase> producer;
    private final KafkaConfig config;

    public KafkaEventProducer(KafkaConfig kafkaConfig) {
        this.config = kafkaConfig;
        this.producer = new KafkaProducer<>(kafkaConfig.getProducerProperties());
    }

    public void sendRecord(ProducerRecord<String, SpecificRecordBase> record) {
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception != null) {
                    System.err.println("Error sending record: " + exception.getMessage());
                } else {
                    System.out.printf("Sent record(key=%s value=%s) " +
                                    "to partition=%d with offset=%d%n",
                            record.key(), record.value(), metadata.partition(), metadata.offset());
                }
            }
        });
    }

}
