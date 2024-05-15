package com.client.instruction;


import com.client.instruction.impl.*;

/**
 * A list of instruction identifiers mapped to their respective instruction.
 * @author Heaven
 */
@SuppressWarnings("unchecked")
public enum InstructionId {
    NOTHING(-1),
    SET_TASK_PROGRESS(2) {
        @Override
        public VoidInstruction getInstruction() {
            return new SetTaskProgress();
        }
    },
    SET_WIDGET_CONTAINER_SIZE(3) {
        @Override
        public VoidInstruction getInstruction() {
            return new SetWidgetContainerSize();
        }
    },
    SET_WIDGET_TEXT_NULL(4) {
        @Override
        public VoidInstruction getInstruction() {
            return new SetWidgetTextNull();
        }
    },
    SET_VARP(8) {
        @Override
        public VoidInstruction getInstruction() {
            return new SetVarp();
        }
    },
    HEALTH_HUD(13) {
        @Override
        public VoidInstruction getInstruction() { return new SetHealthHud(); }
    },
    SET_BLACKJACK_RESET(13_031) {
        @Override
        public VoidInstruction getInstruction() { return new SetBlackjackReset(); }
    },
    SET_BLACKJACK_CARD_SPRITE(13_030) {
        @Override
        public VoidInstruction getInstruction() { return new SetBlackjackCard(); }
    },
    CREATE_GRAND_EXCHANGE_OFFER(13_025) {
        @Override
        public VoidInstruction getInstruction() { return new CreateGrandExchangeOffer(); }
    },
    SET_WIDGET_MOVE(35) {
        @Override
        public VoidInstruction getInstruction() { return new SetWidgetMove(); }
    },
    UPDATE_NEW_NOTIFICATION(3346) {
        @Override
        public VoidInstruction getInstruction() { return new UpdateNewNotification(); }
    },

    ; // End of enum

    public static final InstructionId[] VALUES = values();

    /**
     * The identifier value in the form of a int literal.
     */
    public final int uid;

    /**
     * A fallback instruction if no other is specified.
     */
    public <T> Instruction<T> getInstruction() {
        return null;
    }

    /**
     * A shorthand method to invoking an instruction without
     * @param args
     */
    public void invoke(InstructionArgs args) {
        getInstruction().invoke(args);
    }

    InstructionId(int uid) {
        this.uid = uid;
    }

    public static InstructionId fromId(int id) {
        for (InstructionId insId : VALUES) {
            if (insId.uid == id)
                return insId;
        }
        return NOTHING;
    }
}