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
import com.tll.backend.model.bill.FixedBill;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FixedBillDeserializer extends StdDeserializer<FixedBill> {
    public FixedBillDeserializer() {
        this(null);
    }

    protected FixedBillDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public FixedBill deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.readValueAsTree();

        int id;
        if (treeNode.get("id") instanceof IntNode intId) {
            id = intId.intValue();
        } else if (treeNode.get("id") instanceof TextNode textId) {
            id = Integer.parseInt(textId.textValue());
        } else {
            throw new RuntimeException("Invalid id type");
        }

        int userId;
        if (treeNode.get("userId") instanceof IntNode intUserId) {
            userId = intUserId.intValue();
        } else if (treeNode.get("userId") instanceof TextNode textUserId) {
            userId = Integer.parseInt(textUserId.textValue());
        } else {
            throw new RuntimeException("Invalid userId type");
        }

        Iterator<JsonNode> cart = ((ArrayNode) treeNode.get("cart")).elements();
        List<Pair<Barang, Integer>> pairs = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
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

        return new FixedBill(id, userId, pairs);
    }
}