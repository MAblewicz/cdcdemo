package com.tech3camp.cdcreader.kafka;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech3camp.cdcreader.model.Product;
import com.tech3camp.cdcreader.websocket.ProductController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CDCKafkaReader {
    private final ObjectMapper demoProjectMapper;

    Logger LOGGER = LoggerFactory.getLogger(CDCKafkaReader.class);

    private final ProductController productController;

    public CDCKafkaReader(ObjectMapper demoProjectMapper, ProductController productController) {
        this.demoProjectMapper = demoProjectMapper;
        this.productController = productController;
    }

    @KafkaListener(topics = "${cdcdemo.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenForProduct(String message) {
        try {
            JsonNode jsonNode = demoProjectMapper.readTree(message);
            var operationTypeBody = jsonNode.get("op");
            var afterBody = jsonNode.get("after");
            LOGGER.info("Got Operation type: " + operationTypeBody);

            if (!afterBody.isNull()) {

                LOGGER.info("Got product: " + afterBody);
                var result = demoProjectMapper.readValue(afterBody.toString(), Product.class);
                productController.sendProduct(result);
            } else {
                LOGGER.info("Got Empty product");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Processing Product message END");
    }
}


