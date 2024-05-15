package com.client.instruction.impl;


import com.client.Client;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.BlackJack;
import com.client.instruction.InstructionArgs;
import com.client.instruction.VoidInstruction;
import net.runelite.api.GrandExchangeOffer;
import net.runelite.api.GrandExchangeOfferState;
import net.runelite.api.events.GrandExchangeOfferChanged;

import static com.client.graphics.interfaces.RSInterface.imageLoader;

public class CreateGrandExchangeOffer implements VoidInstruction {

    @Override
    public Void invoke(InstructionArgs args) {
        int totalSelling = args.getNextInt();
        int amountSold = args.getNextInt();
        int itemId = args.getNextInt();
        int currency = args.getNextInt();
        int price = args.getNextInt();
        int slot = args.getNextInt();

        if(currency == 13204)
            price *= 1000;
        StringBuilder b = new StringBuilder();
        b.append("totalSelling= " + totalSelling);
        b.append("\\n");
        b.append("amountSold= " + amountSold);
        b.append("\\n");
        b.append("itemId= " + itemId);
        b.append("\\n");
        b.append("currency= " + currency);
        b.append("\\n");
        b.append("price= " + price);
        b.append("\\n");
        b.append("slot= " + slot);
        b.append("\\n");
        System.out.println(b.toString());
        GrandExchangeOfferState state = amountSold >= totalSelling ? GrandExchangeOfferState.SOLD : GrandExchangeOfferState.SELLING;
        GrandExchangeOfferChanged grandExchangeOfferChanged = new GrandExchangeOfferChanged();
        grandExchangeOfferChanged.setSlot(slot);
        int finalPrice = price;
        grandExchangeOfferChanged.setOffer(new GrandExchangeOffer() {
            @Override
            public int getQuantitySold() {
                return amountSold;
            }

            @Override
            public int getItemId() {
                return itemId;
            }

            @Override
            public int getTotalQuantity() {
                return totalSelling;
            }

            @Override
            public int getPrice() {
                return finalPrice;
            }

            @Override
            public int currency() {
                return currency;
            }

            @Override
            public GrandExchangeOfferState getState() {
                return state;
            }
        });
        Client.instance.getCallbacks().post(grandExchangeOfferChanged);
        return null;
    }
}