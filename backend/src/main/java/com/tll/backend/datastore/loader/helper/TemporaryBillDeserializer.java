package com.tll.backend.datastore.loader.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.TemporaryBill;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TemporaryBillDeserializer extends StdDeserializer<TemporaryBill> {
    public TemporaryBillDeserializer() {
        this(null);
    }

    protected TemporaryBillDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TemporaryBill deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.readValueAsTree();

        int id;
        if (treeNode.get("id") instanceof IntNode intId) {
            id = intId.intValue();
        } else if (treeNode.get("id") instanceof TextNode textId) {
            id = Integer.parseInt(textId.textValue());
        } else {
            throw new RuntimeException("Invalid id type");
        }

        Iterator<JsonNode> cart;
        List<Pair<Barang, Integer>> pairs = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (treeNode.get("cart") instanceof ArrayNode arrayNode) {
            cart = arrayNode.elements();
            while (cart.hasNext()) {
                JsonNode node = cart.next();

                if (node instanceof ObjectNode objectNode) {
                    Barang barang = mapper.convertValue(objectNode.get("barang"), Barang.class);
                    Integer quantity = objectNode.get("jumlah").intValue();
                    pairs.add(Pair.with(barang, quantity));
                } else {
                    throw new RuntimeException("Invalid cart type");
                }
            }
        } else if (treeNode.get("cart") instanceof ObjectNode objectNode) {
            cart = objectNode.elements();
            while (cart.hasNext()){
                JsonNode node = cart.next();
                Barang barang = mapper.convertValue(node, Barang.class);
                node = cart.next();
                Integer quantity = mapper.convertValue(node, Integer.class);

                pairs.add(Pair.with(barang, quantity));
            }
        } else {
            throw new RuntimeException("Invalid cart type");
        }

        TemporaryBill temporaryBill = new TemporaryBill(id);
        temporaryBill.setTemporaryCart(pairs);

        return temporaryBill;
    }
}
