package com.tech3camp.cdcreader.websocket;


import com.tech3camp.cdcreader.model.Product;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {

    private  final SimpMessagingTemplate simpMessagingTemplate;

    public ProductController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendProduct(Product inputProduct) throws Exception {
        simpMessagingTemplate.convertAndSend("/topic/products", inputProduct);
    }

}